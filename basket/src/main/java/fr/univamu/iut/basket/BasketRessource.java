package fr.univamu.iut.basket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;


/**
 * Ressource associée aux Panier
 * (point d'accès de l'API REST)
 */
@Path("/baskets")
@ApplicationScoped
public class BasketRessource {

    /**
     * Service utilisé pour accéder aux données des paniers et CRUD leurs informations
     */

    private BasketService service;


    /**
     * Constructeur par défaut
     */
    public BasketRessource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param basketRepo objet implémentant l'interface d'accès aux données
     * @param product objet implémentant l'interface d'accès aux données de l'api produit
     */
    public @Inject BasketRessource( BasketRepositoryInterface basketRepo,ProductRepositoryInterface product ){
        this.service = new BasketService(basketRepo,product) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux produits
     * @param service
     */
    public BasketRessource( BasketService service ){
        this.service = service;
    }

    /**
     * Endpoint permettant de publier les informations d'un panier dont l'id est passée en paramètre dans le chemin
     * @param id id du panier recherché
     * @return les informations du panier au format JSON
     */
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

    /**
     * Endpoint permettant de publier tous les paniers enregistrés
     * @return la liste des paniers au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllBaskets() {
        return service.getAllBasketsJSON();
    }

    /**
     * Endpoint permettant de créer un panier
     * (la requête post doit fournir toutes les informations sauf l'id qui sera ignorée car il y'a une auto indentation)
     * @param basket le panier transmis en HTTP au format JSON et convertit en objet Basket
     * @return une réponse "The Basket is create" si la création a été effectuée, une erreur sinon
     * @throws Exception une exception liée au fait que le panier n'a pas pu être créé
     */
    @POST
    @Consumes("application/json")
    public Response createBasket(Basket basket) throws Exception{

        if(!service.createBasket(basket))
            throw new Exception();
        else
            return Response.ok("The Basket is create").build();
    }

    /**
     * Endpoint permettant de supprimer le panier dont l'id est passé en paramètre dans le chemin
     * @param id l'id du panier dont il faut changer les informations
     * @return une réponse "The basket is deleted" si la suppression a été effectuée, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    public Response deleteBasket(@PathParam("id") int id){

        //si le produit n'a pas été trouvé
        if(!service.deleteBasket(id))
            throw new NotFoundException();
        else
            return Response.ok("The basket is deleted").build();
    }

    /**
     * Endpoint permettant d'ajouter des produits de l'api Produit et Utilisateur'
     * @param id l'id du produit que l'on veut allez ajouté
     * @param quantity la quantité du produit que l'on veut ajouté
     * @param id_basket l'id du panier dans lequel on veut ajouter le produit
     * @return une réponse "The basket is added" si l'ajout' a été effectuée, une erreur sinon
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerProduct(@FormParam("id") int id,@FormParam("quantity") int quantity,@FormParam("id_basket") int id_basket) throws SQLException, ClassNotFoundException {

        if( service.registerProduct(id,quantity,id_basket) )
            return Response.ok("The Product is added").build();
        else
            return Response.status( Response.Status.CONFLICT ).build();
    }

    /**
     * Endpoint permettant de supprimer le produit dont l'id, le nom et la quantité sont passé en paramètre dans le chemin
     * @param id_basket l'id du panier dont l'on veut supprimer le produit
     * @param name le nom du produit que l'on veut retirer
     * @param quantity la quantité du produit que l'on veut enlever
     * @return une réponse "The Product is deleted" si la suppression a été effectuée,on lève une exception sinon
     * @throws Exception une exception liée au fait que le produit n'existe pas
     */
    @DELETE
    @Path("{id_basket}/{name}/{quantity}")
    public Response deleteProduct(@PathParam("id_basket") int id_basket, @PathParam("name") String name, @PathParam("quantity") int quantity) throws Exception{
        if(!service.deleteProduct(id_basket,name,quantity))
            throw new Exception();
        else
            return Response.ok("The Product is deleted").build();
    }



}