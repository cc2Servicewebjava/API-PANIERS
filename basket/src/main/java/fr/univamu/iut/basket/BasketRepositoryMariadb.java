package fr.univamu.iut.basket;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;


public class BasketRepositoryMariadb implements BasketRepositoryInterface, Closeable {


    protected Connection dbConnection ;

    public BasketRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Basket getBasket(int id) {
        Basket selectedBasket = null;

        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket WHERE Basket.id = ?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
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




    public ArrayList<Basket> getAllBaskets(){
        Basket selectedBasket = null;
        ArrayList<Basket> listBaskets ;

        String query = "SELECT Basket.*, ProductList.id AS product_id, ProductList.name AS product_name, ProductList.price AS product_price, ProductList.quantity AS product_quantity FROM Basket JOIN ProductList ON Basket.id = ProductList.id_basket";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){

            ResultSet result = ps.executeQuery();

            listBaskets = new ArrayList<>();

            ArrayList<Product> product_list = new ArrayList<>();

            while (result.next()) {
                // récupération des informations du panier
                if (selectedBasket == null) {
                    int id = result.getInt("id");
                    String update_date = result.getString("update_date");
                    boolean authentication = result.getBoolean("authentication");
                    selectedBasket = new Basket(id, update_date, authentication, product_list);

                    listBaskets.add(selectedBasket);

                }
                // ajout du produit à la liste
                int product_id = result.getInt("product_id");
                String product_name = result.getString("product_name");
                int product_price = result.getInt("product_price");
                int product_quantity = result.getInt("product_quantity");
                product_list.add(new Product(product_id, product_name, product_price, product_quantity,result.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }

}