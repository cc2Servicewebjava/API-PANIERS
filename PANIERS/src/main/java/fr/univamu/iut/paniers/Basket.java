package fr.univamu.iut.paniers;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un panier
 */
public class Basket {


    protected int id;

    protected String update_date;

    protected boolean authentication;

    protected ArrayList<Product> product_list;


    public Basket() {
    }


    public Basket(int id, String update_date, boolean authentication ,ArrayList<Product> product_list ) {
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

    public boolean getAuthentication() {
        return authentication;
    }
    public List getProduct_list(){
        return product_list;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }


    public void setAuthentication(boolean authentication) {
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