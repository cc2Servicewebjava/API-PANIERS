package fr.univamu.iut.paniers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class BasketApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface BasketRepositoryInterface utilisée
     *          pour accéder aux données des livres, voire les modifier
     */
    @Produces
    private BasketRepositoryInterface openDbConnection(){
        BasketRepositoryMariadb db = null;

        try{
            db = new BasketRepositoryMariadb("jdbc:mariadb://mysql-tpphp1.alwaysdata.net/tpphp1_paniers_db ", "tpphp1_paniers ", "tpphp1_paniers _mdp");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param BasketRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes BasketRepositoryInterface BasketRepo ) {
        BasketRepo.close();
    }
}