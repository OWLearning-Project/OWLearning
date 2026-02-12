package app.OwLearning.Domain.Ports.IServices;

import app.OwLearning.Domain.Models.Utilisateur;

/**
 * Interface de ServiceToken qui permet de générer un token, le valider, l'invalider et extraire l'ID du token
 */
public interface IServiceToken
{
    /**
     * Méthode qui permet de générer un token
     * @param utilisateur
     * @return le token généré
     */
    String genererToken(Utilisateur utilisateur);

    /**
     * Méthode qui permet de valider un Token
     * @param token
     * @return true si le token est valider false sinon
     */
    boolean validerToken(String token);

    /**
     * Méthode qui permet d'invalider un token
     * @param token
     */
    void invaliderToken(String token);

    /**
     * Méthode qui permet d'extraire l'ID du token généré
     * @param token
     * @return l'ID extrait
     */
    int extraireID(String token);
}
