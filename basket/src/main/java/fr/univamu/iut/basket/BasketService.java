package fr.univamu.iut.basket;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class BasketService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les paniers
     */
    protected BasketRepositoryInterface basketRepo ;

    /**
     * Objet permettant d'accéder au dépôt API où sont stockées les informations sur les produit
     */
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param basketRepo objet implémentant l'interface d'accès aux données
     * @param productRepo objet implémentant l'interface d'accès aux données
     */
    public  BasketService( BasketRepositoryInterface basketRepo,ProductRepositoryInterface productRepo) {
        this.basketRepo = basketRepo;
        this.productRepo = productRepo;

    }

    /**
     * Méthode retournant au format JSON les informations sur un panier recherché
     * @param id l'id du panier recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getBasketJSON(int id){
        String result = null;
        Basket myBasket = basketRepo.getBasket(id);

        // si le livre a été trouvé
        if( myBasket != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myBasket);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode retournant les informations sur les paniers au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllBasketsJSON(){

        ArrayList<Basket> allBaskets = (ArrayList<Basket>) basketRepo.getAllBaskets();


        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allBaskets);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode permettant de créer un panier
     * @param basket objet Basket représentant le panier à crée
     * @return true si le panier a pu être créé
     */
    public Boolean createBasket(Basket basket){return basketRepo.createBasket(basket);}

    public Boolean AddProduct(Product product){
        return basketRepo.AddProduct(product);
    }

    /**
     * Méthode permettant de supprimer un panier
     * @param id id du panier à supprimer
     * @return true si le panier a pu être supprimé
     */
    public boolean deleteBasket(int id){return basketRepo.deleteBasket(id);}

    /**
     * Méthode permettant de supprimer un produit
     * @param id_basket l'id du panier dont l'on veut supprimer le produit
     * @param name le nom du produit que l'on veut retirer
     * @param quantity la quantité du produit que l'on veut enlever
     * @return true si le produit a pu être supprimé
     */
    public boolean deleteProduct(int id_basket, String name, int quantity){return basketRepo.deleteProduct(id_basket,name,quantity);}



    /**
     * Méthode permettant d'ajouter des produit à partir de l'API Produit et Utilisateur
     * @param id l'id du produit que l'on veut allez ajouté
     * @param quantity la quantité du produit que l'on veut ajouté
     * @param id_basket l'id du panier dans lequel on veut ajouter le produit
     * @return true si le produit existe et qu'il a bien était ajouter , return false si le produit n'existe pas ou n'est pas disponible
     */
    public boolean registerProduct(int id,int quantity,int id_basket) throws SQLException, ClassNotFoundException {

        boolean result = false;


        Product myProduct = productRepo.getProduct(id);


        if (myProduct == null){
            throw new RuntimeException("The Product not exists");

        }
        else if(myProduct.getDisponibility() == false){
            throw new RuntimeException("The Product is not avaible");
        }
        else {
            Product product = new Product(myProduct.getId(),myProduct.getName(),myProduct.getPrice(),quantity,id_basket,myProduct.getUnit(),myProduct.getDisponibility());
            BasketRepositoryMariadb repository = new BasketRepositoryMariadb("jdbc:mariadb://mysql-tpphp1.alwaysdata.net/tpphp1_paniers_db","tpphp1_paniers","tpphp1_paniers_mdp");
            repository.AddProduct(product);
            result = true;
        }
        return result;


    }

}
