package fr.univamu.iut.basket;

import java.util.List;

/**
 * Interface d'accès aux données des paniers
 */
public interface BasketRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les paniers
     */
    public void close();

    /**
     * Méthode retournant le panier dont l'id est passée en paramètre
     * @param id identifiant du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    public Basket getBasket( int id );

    /**
     * Méthode retournant la liste des paniers
     * @return une liste d'objets paniers
     */
    public List getAllBaskets();


    /**
     * Méthode permettant de créer un panier
     * @param basket objet Basket représentant les données du panier à créer
     * @return The Basket is create si la création a bien eu lieux,The Basket is not create sinon
     */
    public boolean createBasket(Basket basket);


    /**
     * Méthode permettant de supprimer un panier enregistré
     * @param id identifiant du panier à supprimer
     * @return Basket is deleted si le panier existait et la suppression a été faite, Basket is not deleted sinon
     */
    boolean deleteBasket(int id);

    /**
     * Méthode permettant d'ajouter des produit à un panier depuis une API
     * @param product objet Product représentant les données du panier à créer
     * @return Product is added si l'ajout' a bien eu lieux,Product is not added sinon
     */
    boolean AddProduct(Product product);


    /**
     * Méthode permettant de supprimer un panier enregistré
     * @param id_basket identifiant du panier
     * @param productName nom du produit à supprimer
     * @param quantity quantité du produit en question à supprimer
     * @return Product is deleted si le panier existait et la suppression a été faite, Product is not deleted sinon
     */
    boolean deleteProduct(int id_basket, String productName, int quantity);
}
