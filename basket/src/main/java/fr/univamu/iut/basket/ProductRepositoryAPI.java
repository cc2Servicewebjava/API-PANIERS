package fr.univamu.iut.basket;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *Class permettant d'utiliser les méthodes de l'api
 */
public class ProductRepositoryAPI implements ProductRepositoryInterface {

    /**
     * URL de l'API des produit
     */
    String url;

    /**
     * Constructeur initialisant l'url de l'API
     * @param url chaîne de caractères avec l'url de l'API
     */
    public ProductRepositoryAPI(String url){
        this.url = url ;
    }
    /**
     * Méthode fesant appel à une API et retournant le produit dont l'id est passée en paramètre
     *
     * @param id identifiant du produit recherché
     * @return un objet Product sous format JSON représentant le produit recherché
     */
    @Override
    public Product getProduct(int id) {
        Product product = null;

        // création d'un client
        Client client = ClientBuilder.newClient();
        // définition de l'adresse de la ressource
        WebTarget productResource  = client.target(url);
        // définition du point d'accès
        WebTarget productEndpoint = productResource.path("products/"+id);
        // envoi de la requête et récupération de la réponse
        Response response = productEndpoint.request(MediaType.APPLICATION_JSON).get();

        // si le produit a bien été trouvé, conversion du JSON en Produit
        if( response.getStatus() == 200)
            product = response.readEntity(Product.class);

        // fermeture de la connexion
        client.close();
        return product;
    }


}