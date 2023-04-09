package fr.univamu.iut.basket;


/**
 * Classe représentant un produit
 */
public class Product {
    /**
     * Id du produit
     */
    protected int id;

    /**
     * Nom du produit
     */
    protected String name;

    /**
     * Prix du produit
     */
    protected int price;

    /**
     * quantité de produit
     */
    protected int quantity;

    /**
     * référence du panier
     */
    protected int id_basket;

    /**
     * Disponibilité du produit
     */
    protected boolean disponibility;

    /**
     * Unité du produit
     * les valeurs proviennent de l'enum EnumUnit
     */
    protected EnumUnit unit;




    public Product(){

    }
    /**
     * Constructeur du produit
     * @param id id du produit
     * @param name nom du produit
     * @param price prix du produit
     * @param quantity quantité du produit
     * @param id_basket réference du panier
     * @param unit unité du produit
     * @param disponibility disponibilité du produit
     */

    public Product(int id, String name, int price, int quantity, int id_basket,EnumUnit unit, boolean disponibility) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id_basket = id_basket;
        this.disponibility = disponibility;
        this.unit = unit;

    }
    /**
     * Méthode permettant d'accéder à la référence du produit
     * @return un entier représentant l'id du produit
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode permettant d'accéder au Nom du produit
     * @return une chaîne de caractère correspondant au nom du produit
     */
    public String getName() {
        return name;
    }

    /**
     * Méthode permettant d'accéder au prix du produit
     * @return un int représentant le prix du produit
     */
    public int getPrice() {
        return price;
    }

    /**
     * Méthode permettant d'accéder au quantité du produit
     * @return un int représentant la quantité de produit
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Méthode permettant d'accéder à la référence du panier
     * @return un int représentant la référence du panier
     */
    public int getId_basket() {
        return id_basket;
    }

    /**
     * Méthode permettant d'accéder à la disponibilité du produit
     * @return un booléen représentant la disponibilité du produit
     */
    public boolean getDisponibility() {
        return disponibility;
    }

    /**
     * Méthode permettant d'accéder à l'unité du produit
     * @return une valeur de l'EnumUnit représentant l'unité du produit
     */
    public EnumUnit getUnit() {
        return unit;
    }

    /**
     * Méthode permettant de modifier l'id du produit
     * @param id un int représentant l'id à utiliser
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode permettant de modifier le nom du produit
     * @param name une chaîne de caractère représentant le nom à utiliser
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Méthode permettant de modifier le prix du produit
     * @param price un int représentant le prix à utiliser
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Méthode permettant de modifier la quantité de produit
     * @param quantity un int représentant la quantité de produit à utiliser
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Méthode permettant de modifier la référence du panier
     * @param id_basket un int représentant la référence du panier que l'on veut utiliser
     */
    public void setId_basket(int id_basket) {
        this.id_basket = id_basket;
    }

    /**
     * Méthode permettant de modifier la disponibilité du produit
     * @param disponibility un booléen représentant la disponibilité du produit, true si disponible sinon false
     */
    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    /**
     * Méthode permettant de modifier l'unité du produit
     * @param unit une valeur de EnumUnit représentant l'unité à utiliser
     */
    public void setUnit(EnumUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", id_basket=" + id_basket +
                ", disponibility=" + disponibility +
                ", unit=" + unit +
                '}';
    }
}
