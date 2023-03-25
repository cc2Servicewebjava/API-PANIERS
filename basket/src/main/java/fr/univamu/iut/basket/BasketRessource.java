package fr.univamu.iut.basket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


/**
 * Ressource associée aux livres
 * (point d'accès de l'API REST)
 */
@Path("/baskets")
@ApplicationScoped
public class BasketRessource {


    private BasketService service;


    public BasketRessource(){}


    public @Inject BasketRessource( BasketRepositoryInterface basketRepo ){
        this.service = new BasketService(basketRepo) ;
    }


    public BasketRessource( BasketService service ){
        this.service = service;
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getBasketJSON( @PathParam("id") int id){

        String result = service.getBasketJSON(id);

        // si le livre n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllBasketsJSON();
    }


}