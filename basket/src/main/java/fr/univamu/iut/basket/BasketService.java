package fr.univamu.iut.basket;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class BasketService {

    protected BasketRepositoryInterface basketRepo ;

    public  BasketService( BasketRepositoryInterface basketRepo) {
        this.basketRepo = basketRepo;
    }

    public String getBasketJSON(int id){
        String result = null;
        Basket myBasket = basketRepo.getBasket(id);

        // si le livre a été trouvé
        if( myBasket != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myBasket);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public String getAllBasketsJSON(){

        ArrayList<Basket> allBaskets = (ArrayList<Basket>) basketRepo.getAllBaskets();


        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allBaskets);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

}
