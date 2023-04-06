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
    public String getAllBaskets() {
        return service.getAllBasketsJSON();
    }


    @POST
    @Consumes("application/json")
    public Response createBasket(Basket basket) throws Exception{

        if(!service.createBasket(basket))
            throw new Exception();
        else
            return Response.ok("The Basket is create").build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteBasket(@PathParam("id") int id){

        //si le produit n'a pas été trouvé
        if(!service.deleteBasket(id))
            throw new NotFoundException();
        else
            return Response.ok("The basket is deleted").build();
    }

    @POST
    @Consumes("application/json")
    public Response AddProduct(Product product) throws Exception{

        if(!service.AddProduct(product))
            throw new Exception();
        else
            return Response.ok("The Product is added").build();
    }

    @POST
    @Path("{id_basket}/{name}/{quantity}")
    @Consumes("application/json")
    public Response deleteProduct(@PathParam("id_basket") int id_basket, @PathParam("name") String name, @PathParam("quantity") int quantity) throws Exception{
        if(!service.deleteProduct(id_basket,name,quantity))
            throw new Exception();
        else
            return Response.ok("The Product is deleted").build();
    }



}