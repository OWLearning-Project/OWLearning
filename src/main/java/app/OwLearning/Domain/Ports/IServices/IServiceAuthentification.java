package app.OwLearning.Domain.Ports.IServices;

import app.OwLearning.Shared.Exceptions.ExceptionCompteExistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdentifiants;

/**
 * Interface de ServiceAuthentification qui permet d'inscrire de connecter et de deconnecter un utilisateur.
 */
public interface IServiceAuthentification
{
    /**
     * Méthode qui permet d'inscrire un utilsateur
     * @param nom
     * @param prenom
     * @param email
     * @param mdp
     * @param age
     * @param niveauEtude
     * @param role
     * @throws ExceptionCompteExistant
     */
    public abstract void inscription(String nom, String prenom, String email, String mdp, int age, String niveauEtude, String role) throws ExceptionCompteExistant;

    /**
     * Méthode qui permet de se connecter à l'application
     * @param email
     * @param mdp
     * @return le token de connexion
     * @throws ExceptionMauvaisIdentifiants
     */
    public abstract String connexion(String email,String mdp) throws ExceptionMauvaisIdentifiants;

    /**
     * Méthode qui permet de se déconnecter
     * @param token
     * @return true si déconnexion réussi false sinon
     * @throws ExceptionMauvaisIdentifiants
     */
    public abstract boolean deconnexion(String token) throws ExceptionMauvaisIdentifiants;
}
