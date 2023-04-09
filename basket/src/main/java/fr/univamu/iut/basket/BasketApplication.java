package fr.univamu.iut.basket;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Classe implémentant l'ouverture et la fermeture de la connection à la bd
 */
@ApplicationPath("/api")
@ApplicationScoped
public class BasketApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de donnée au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface BasketRepositoryInterface utilisée pour accéder aux données
     * des utilisateurs et les CRUD qui permet également de pouvoir se connecter à l'API Produit et Utilisateur
     */

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


    @Produces
    private ProductRepositoryInterface connectProductApi(){
        return new ProductRepositoryAPI("http://localhost:8080/produits_et_utilisateurs-1.0-SNAPSHOT/api/");
    }

}
