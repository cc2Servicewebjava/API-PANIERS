package fr.univamu.iut.basket;

import java.util.List;

public interface BasketRepositoryInterface {

        public void close();

        public Basket getBasket( int id );

        public List getAllBaskets();




}
