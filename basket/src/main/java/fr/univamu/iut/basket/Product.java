package fr.univamu.iut.basket;


/**
 * Classe représentant un produit
 */
public class Product {
    protected int id;

    protected String name;

    protected int price;

    protected int quantity;

    protected int id_basket;


    public Product(){

    }

    public Product(int id, String name, int price, int quantity, int id_basket) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id_basket = id_basket;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId_basket() {
        return id_basket;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId_basket(int id_basket) {
        this.id_basket = id_basket;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", id_basket=" + id_basket +
                '}';
    }
}
