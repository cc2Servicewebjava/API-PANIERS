package fr.univamu.iut.paniers;


/**
 * Classe représentant un produit
 */
public class Product {
    protected int id;

    protected int price;

    protected int quantity;

    protected int id_basket;


    public Product(){

    }

    public Product(int id, int price, int quantity, int id_basket) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.id_basket = id_basket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_basket() {
        return id_basket;
    }

    public void setId_basket(int id_basket) {
        this.id_basket = id_basket;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", id_basket=" + id_basket +
                '}';
    }
}
