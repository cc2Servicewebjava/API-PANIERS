package fr.univamu.iut.paniers.;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BasketRepositoryMariadb   implements BasketRepositoryInterface, Closeable {


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
    public Basket getBasket(String id) {

        Basket selectedBasket = null;

        String query = "SELECT * FROM Basket WHERE id=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du livre est valide)
            if( result.next() )
            {
                int id = result.getInt("id");
                int update_date = result.getInt("update_date");
                boolean authentication = result.getBoolean("authentication");
                Array product_list = result.getArray("product_list");

                // création et initialisation de l'objet Book
                selectedBasket = new Basket(,update_date, authentication, product_list);
                selectedBook.setStatus(status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBook;
    }







    @Override
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> listBooks ;

        String query = "SELECT * FROM Book";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listBooks = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                String reference = result.getString("reference");
                String title = result.getString("title");
                String authors = result.getString("authors");
                char status = result.getString("status").charAt(0);

                // création du livre courant
                Book currentBook = new Book(reference, title, authors);
                currentBook.setStatus(status);

                listBooks.add(currentBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBooks;
    }


}