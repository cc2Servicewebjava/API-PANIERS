package fr.univamu.iut.basket;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class BasketApplication extends Application {

    @Produces
    private BasketRepositoryInterface openDbConnection(){
        BasketRepositoryMariadb db = null;

        try{
            db = new BasketRepositoryMariadb("jdbc:mariadb://mysql-tpphp1.alwaysdata.net/tpphp1_paniers_db","tpphp1_paniers","tpphp1_paniers_mdp");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes BasketRepositoryInterface basketRepo ) {
        basketRepo.close();
    }
}