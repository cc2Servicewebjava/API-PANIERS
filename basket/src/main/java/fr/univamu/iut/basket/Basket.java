package fr.univamu.iut.basket;

import java.util.ArrayList;


/**
 * Classe représentant un panier
 */
public class Basket {

    /**
     * Id du panier
     */
    protected int id;
    /**
     * Date de Mise à jour du panier
     */
    protected String update_date;

    /**
     * Validation de panier
     */

    protected boolean authentication;

    /**
     * liste des produits
     */

    protected ArrayList<Product> product_list;


    public Basket() {
    }

    /**
     * Constructeur du panier
     * @param id id du panier
     * @param update_date date de mise à jour du panier
     * @param authentication validation du panier
     * @param product_list Liste des produits
     */
    public Basket(int id, String update_date, boolean authentication ,ArrayList<Product> product_list ) {
        this.id = id;
        this.update_date = update_date;
        this.authentication = authentication;
        this.product_list = product_list;

    }

    /**
     * Méthode permettant d'accéder à la référence du panier
     * @return un entier représentant l'id du panier
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode permettant d'accéder à la date de Mise à jour du panier
     * @return une string représentant la date
     */
    public String getUpdate_date() {
        return update_date;
    }

    /**
     * Méthode permettant d'accéder à la validation du panier
     * @return un booléen représentant true ou false respectivement validé et as validé
     */
    public boolean getAuthentication() {
        return authentication;
    }

    /**
     * Méthode permettant d'accéder à la liste des produits
     * @return une ArrayList qui représente les produits
     */
    public ArrayList getProduct_list(){
        return product_list;
    }

    /**
     * Méthode permettant de modifier l'id du panier
     * @param id un int représentant l'id à utiliser
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode permettant de modifier la date de mise à jour du produit
     * @param update_date une String représentant la date à mettre à jour à utiliser
     */
    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    /**
     * Méthode permettant de modifier la vérification
     * @param authentication un booléen représentant la vérification à utiliser
     */
    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    /**
     * Méthode permettant de modifier la liste des produits
     * @param product_list une ArrayList représentant la liste des produits à utiliser
     */
    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id='" + id + '\'' +
                ", update_date='" + update_date + '\'' +
                ", authentication='" + authentication + '\'' +
                '}'+'\n';
    }
}