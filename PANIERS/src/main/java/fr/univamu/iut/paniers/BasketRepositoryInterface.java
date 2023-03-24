package fr.univamu.iut.paniers;

import java.util.List;

public interface BasketRepositoryInterface {

        public void close();

        public Basket getBasket( String id );

        public List getAllBasket();


}
