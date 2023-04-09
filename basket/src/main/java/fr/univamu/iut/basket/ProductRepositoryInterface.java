package fr.univamu.iut.basket;

/**
 * Interface d'accès aux données des produits
 */
public interface ProductRepositoryInterface {

    /**
     * Méthode retournant le produit dont l'id est passée en paramètre
     * @param id identifiant du produit recherché
     * @return un objet Product représentant le produit recherché
     */
    public Product getProduct(int id);
}
