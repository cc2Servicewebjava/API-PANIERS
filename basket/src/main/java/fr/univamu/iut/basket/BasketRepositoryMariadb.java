package fr.univamu.iut.basket;

import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accéder aux Panier stockés dans une base de données Mariadb
 */
public class BasketRepositoryMariadb implements BasketRepositoryInterface, Closeable {
    /**
     * Accès à la base de données (session)
     */

    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     * @throws java.sql.SQLException une exception, car il y a un problem de SQL
     * @throws java.lang.ClassNotFoundException une exception, car une classe n'a pas été trouvé (probablement org.mariadb.jdbc.Driver)
     */
    public BasketRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les paniers
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Méthode retournant le panier dont l'id est passée en paramètre
     *
     * @param id identifiant du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    @Override
    public Basket getBasket(int id) {
        Basket selectedBasket = null;

        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity ,ProductList.unit AS product_unit,ProductList.disponibility AS product_disponibility FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket WHERE Basket.id = ?";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(id));

            ResultSet result = ps.executeQuery();

            ArrayList<Product> product_list = new ArrayList<>();

            while (result.next()) {
                // récupération des informations du panier
                if (selectedBasket == null) {
                    String update_date = result.getString("update_date");
                    boolean authentication = result.getBoolean("authentication");
                    selectedBasket = new Basket(id, update_date, authentication, product_list);
                }
                // ajout du produit à la liste
                int product_id = result.getInt("product_id");
                String product_name = result.getString("product_name");
                int product_price = result.getInt("product_price");
                int product_quantity = result.getInt("product_quantity");
                EnumUnit product_unit = EnumUnit.valueOf(result.getString("product_unit"));
                boolean product_disponibility = result.getBoolean("product_disponibility");
                product_list.add(new Product(product_id, product_name, product_price, product_quantity, id,product_unit, product_disponibility));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBasket;
    }

    /**
     * Méthode retournant la liste des Paniers
     *
     * @return une liste d'objets panier
     */
    public ArrayList<Basket> getAllBaskets() {
        ArrayList<Basket> listBaskets = new ArrayList<>();
        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity ,ProductList.unit AS product_unit,ProductList.disponibility AS product_disponibility FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();
            int currentBasketId = -1;
            ArrayList<Product> currentProductList = new ArrayList<>();
            while (result.next()) {

                int basketId = result.getInt("id");
                if (basketId != currentBasketId) {
                    currentBasketId = basketId;
                    currentProductList = new ArrayList<>();
                    Basket selectedBasket = new Basket(basketId, result.getString("update_date"), result.getBoolean("authentication"), currentProductList);
                    listBaskets.add(selectedBasket);
                }
                int productId = result.getInt("product_id");
                String productName = result.getString("product_name");
                int productPrice = result.getInt("product_price");
                int productQuantity = result.getInt("product_quantity");
                EnumUnit product_unit = EnumUnit.valueOf(result.getString("product_unit"));
                boolean product_disponibility = result.getBoolean("product_disponibility");
                Product product = new Product(productId, productName, productPrice, productQuantity, basketId,product_unit,product_disponibility);
                currentProductList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }

    /**
     * Méthode permettant de créer un panier
     *
     * @param basket objet Panier représentant les données du panier à créer
     * @return true si la création a bien eu lieux, false sinon
     */
    @Override
    public boolean createBasket(Basket basket) {
        String query = "INSERT INTO Basket (update_date,authentication) VALUES (?,?)";
        int nbRowInserted = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, basket.getUpdate_date());
            ps.setBoolean(2, basket.getAuthentication());

            nbRowInserted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowInserted != 0);
    }

    /**
     * Méthode permettant de supprimer un panier enregistré
     *
     * @param id identifiant du panier à supprimer
     * @return true si le panier existait et la suppression a été faite, false sinon
     */
    @Override
    public boolean deleteBasket(int id) {
        String query = "DELETE FROM Basket WHERE id=?";
        int nbRowDelete = 0;

        //construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)){
            ps.setInt(1,id);

            //exécution de la requête
            nbRowDelete = ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return (nbRowDelete != 0);
    }

    /**
     * Méthode permettant d'ajouter des produit à un panier
     *
     * @param product objet Product représentant les données du produit à ajouter
     * @return true si l'ajout a bien eu lieux, false sinon
     */
    @Override
    public boolean AddProduct(Product product) {
        String queryCheck = "SELECT * FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryInsert = "INSERT INTO ProductList (name, price, quantity, id_basket, unit, disponibility) VALUES (?, ?, ?, ?, ?, ?)";
        String queryUpdate = "UPDATE ProductList SET quantity = quantity + ?, price = ? WHERE name = ? AND id_basket = ?";
        int nbRowUpdated = 0;
        int nbRowInserted = 0;

        try (PreparedStatement psCheck = dbConnection.prepareStatement(queryCheck)) {
            psCheck.setString(1, product.getName());
            psCheck.setInt(2, product.getId_basket());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                // Le produit existe déjà, mise à jour de la quantité et du prix
                int newQuantity = rs.getInt("quantity") + product.getQuantity();
                int newPrice = rs.getInt("price") + (product.getPrice() * product.getQuantity());
                try (PreparedStatement psUpdate = dbConnection.prepareStatement(queryUpdate)) {
                    psUpdate.setInt(1, product.getQuantity());
                    psUpdate.setInt(2, newPrice);
                    psUpdate.setString(3, product.getName());
                    psUpdate.setInt(4, product.getId_basket());
                    nbRowUpdated = psUpdate.executeUpdate();
                }
            } else {
                // Le produit n'existe pas encore, insertion d'une nouvelle ligne
                int price = product.getPrice() * product.getQuantity();
                try (PreparedStatement psInsert = dbConnection.prepareStatement(queryInsert)) {
                    psInsert.setString(1, product.getName());
                    psInsert.setInt(2, price);
                    psInsert.setInt(3, product.getQuantity());
                    psInsert.setInt(4, product.getId_basket());
                    psInsert.setString(5, product.getUnit().toString());
                    psInsert.setBoolean(6, product.getDisponibility());
                    nbRowInserted = psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowUpdated != 0 || nbRowInserted != 0);
    }


    /**
     * Méthode permettant de supprimer des produits d'un paniers
     *
     * @param id_basket identifiant du panier à supprimer
     * @param name nom du produit que l'on doit supprimer
     * @param quantity quantité de produit que l'on doit retiré
     * @return true si le produit existait et la suppression a été faite, false sinon
     */
    @Override
    public boolean deleteProduct(int id_basket, String name, int quantity) {
        String queryCheck = "SELECT * FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryDelete = "DELETE FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryUpdate = "UPDATE ProductList SET quantity = ?, price = ? WHERE name = ? AND id_basket = ?";
        int currentQuantity = 0;
        int nbRowDeleted = 0;
        boolean result = false;

        try (PreparedStatement psCheck = dbConnection.prepareStatement(queryCheck)) {
            psCheck.setString(1, name);
            psCheck.setInt(2, id_basket);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                // Le produit existe déjà dans le panier, récupération de la quantité actuelle
                currentQuantity = rs.getInt("quantity");

                if (quantity >= currentQuantity) {
                    // La quantité demandée est supérieure ou égale à la quantité actuelle, suppression de la ligne
                    try (PreparedStatement psDelete = dbConnection.prepareStatement(queryDelete)) {
                        psDelete.setString(1, name);
                        psDelete.setInt(2, id_basket);
                        nbRowDeleted = psDelete.executeUpdate();
                        result = (nbRowDeleted != 0);
                    }
                } else {
                    // La quantité demandée est inférieure à la quantité actuelle, mise à jour de la quantité et du prix
                    int unitPrice = rs.getInt("price") / currentQuantity; // Calcul du prix unitaire
                    int newQuantity = currentQuantity - quantity;
                    int newPrice = unitPrice * newQuantity; // Calcul du nouveau prix
                    try (PreparedStatement psUpdate = dbConnection.prepareStatement(queryUpdate)) {
                        psUpdate.setInt(1, newQuantity);
                        psUpdate.setInt(2, newPrice);
                        psUpdate.setString(3, name);
                        psUpdate.setInt(4, id_basket);
                        nbRowDeleted = psUpdate.executeUpdate();
                        result = (nbRowDeleted != 0);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }





}


