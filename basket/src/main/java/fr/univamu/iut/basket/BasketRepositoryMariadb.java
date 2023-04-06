package fr.univamu.iut.basket;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;


public class BasketRepositoryMariadb implements BasketRepositoryInterface, Closeable {


    protected Connection dbConnection;

    public BasketRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Basket getBasket(int id) {
        Basket selectedBasket = null;

        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket WHERE Basket.id = ?";

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
                product_list.add(new Product(product_id, product_name, product_price, product_quantity, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBasket;
    }


    public ArrayList<Basket> getAllBaskets() {
        ArrayList<Basket> listBaskets = new ArrayList<>();
        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket";

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
                Product product = new Product(productId, productName, productPrice, productQuantity, basketId);
                currentProductList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }

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

    @Override
    public boolean AddProduct(Product product) {
        String queryCheck = "SELECT * FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryInsert = "INSERT INTO ProductList (name, price, quantity, id_basket) VALUES (?, ?, ?, ?)";
        String queryUpdate = "UPDATE ProductList SET quantity = quantity + ? WHERE name = ? AND id_basket = ?";
        int nbRowUpdated = 0;
        int nbRowInserted = 0;

        try (PreparedStatement psCheck = dbConnection.prepareStatement(queryCheck)) {
            psCheck.setString(1, product.getName());
            psCheck.setInt(2, product.getId_basket());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                // Le produit existe déjà, mise à jour de la quantité
                try (PreparedStatement psUpdate = dbConnection.prepareStatement(queryUpdate)) {
                    psUpdate.setInt(1, product.getQuantity());
                    psUpdate.setString(2, product.getName());
                    psUpdate.setInt(3, product.getId_basket());
                    nbRowUpdated = psUpdate.executeUpdate();
                }
            } else {
                // Le produit n'existe pas encore, insertion d'une nouvelle ligne
                try (PreparedStatement psInsert = dbConnection.prepareStatement(queryInsert)) {
                    psInsert.setString(1, product.getName());
                    psInsert.setInt(2, product.getPrice());
                    psInsert.setInt(3, product.getQuantity());
                    psInsert.setInt(4, product.getId_basket());
                    nbRowInserted = psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowUpdated != 0 || nbRowInserted != 0);
}
    @Override
    public boolean deleteProduct(int id_basket, String name, int quantity) {
        String queryCheck = "SELECT * FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryDelete = "DELETE FROM ProductList WHERE name = ? AND id_basket = ?";
        String queryUpdate = "UPDATE ProductList SET quantity = ? WHERE name = ? AND id_basket = ?";
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
                    // La quantité demandée est inférieure à la quantité actuelle, mise à jour de la quantité
                    try (PreparedStatement psUpdate = dbConnection.prepareStatement(queryUpdate)) {
                        psUpdate.setInt(1, currentQuantity - quantity);
                        psUpdate.setString(2, name);
                        psUpdate.setInt(3, id_basket);
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


