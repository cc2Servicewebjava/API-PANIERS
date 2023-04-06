package fr.univamu.iut.basket;

import java.util.List;

public interface BasketRepositoryInterface {

        public void close();

        public Basket getBasket( int id );

        public List getAllBaskets();


   public boolean createBasket(Basket basket);


    boolean deleteBasket(int id);

    boolean AddProduct(Product product);


    boolean deleteProduct(int id_basket, String productName, int quantity);
}
