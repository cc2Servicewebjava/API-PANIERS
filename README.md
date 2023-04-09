<h1 align="center">Guide d’utilisation API Panier <br></h1>

### Pour accéder à tous les paniers disponibles il suffit de faire : 

- Lien : http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets 

<img src="https://media.discordapp.net/attachments/927636625314431059/1094674390089732218/image.png?width=1020&height=205" width="800" height="300">

### Pour accéder à un panier en particulier il faudra spécifier l’id : 

- Lien : http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets/{iddupanier} 

<img src="https://media.discordapp.net/attachments/927636625314431059/1094674536038940692/image.png?width=1020&height=162" width="800" height="200">


### Pour pouvoir crée un panier il faudra spécifier la date de mise à jour (update date) et la vérification (authentication) , l’id ne sera pas nécessaire car il y’a une indentation automatique : 

- Lien : POST http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets 

Content-Type : application/json 

{" update date " : "08/04/2023", "authentication": 1} 

 

### Pour pouvoir supprimer un panier il faudra spécifier l’id du panier à supprimer :  

- Lien : DELETE http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets/{id du panier} 

 

### Pour pouvoir ajouter un produit contenu dans l’API produit et utilisateur il faudra rentrer l’id du produit que l’on veut ajouter, la quantité (quantity) du produit que l'on veut ajouter et enfin l'id du panier (id_basket) dans lequel on veut ajouter le produit voici un exemple de requete curl pour effectuer ceci : 

- Requête : curl -X POST -d "id=1&quantity=3&id_basket=2" http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets/ 

Ou on peut le tester sur l’ide intelijidea de cette manière :  

POST http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets 
Content-Type: application/x-www-form-urlencoded 
 
id=1&quantity=3&id_basket=2 

 

 

### Pour pouvoir supprimer un produit on précise l’id du panier (id_basket) dans lequel on veut supprimer le produit puis le nom du produit que l’on veut supprimer (name) et enfin le nombre de quantité qu’on doit lui retirer : 

- Lien :  DELETE http://localhost:8080/basket-1.0-SNAPSHOT/api/baskets/{{id_basket}}/{{name}}/{{quantity}} 
