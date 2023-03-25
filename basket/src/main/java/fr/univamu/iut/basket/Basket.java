package fr.univamu.iut.basket;

import java.util.ArrayList;


/**
 * Classe représentant un panier
 */
public class Basket {


    protected int id;

    protected String update_date;

    protected int authentication;

    protected ArrayList<Product> product_list;


    public Basket() {
    }


    public Basket(int id, String update_date, int authentication ,ArrayList<Product> product_list ) {
        this.id = id;
        this.update_date = update_date;
        this.authentication = authentication;
        this.product_list = product_list;

    }


    public int getId() {
        return id;
    }


    public String getUpdate_date() {
        return update_date;
    }

    public int getAuthentication() {
        return authentication;
    }
    public ArrayList getProduct_list(){
        return product_list;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }


    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id='" + id + '\'' +
                ", update_date='" + update_date + '\'' +
                ", authentication='" + authentication + '\'' +
                '}';
    }
}