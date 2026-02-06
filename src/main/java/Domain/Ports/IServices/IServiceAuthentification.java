package Domain.Ports.IServices;

import Shared.Exceptions.ExceptionCompteExistant;
import Shared.Exceptions.ExceptionMauvaisIdentifiants;

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
     * @return true si inscription réussi false sinon
     * @throws ExceptionCompteExistant
     */
    public boolean inscription(String nom, String prenom, String email, String mdp, int age, String niveauEtude, String role) throws ExceptionCompteExistant;

    /**
     * Méthode qui permet de se connecter à l'application
     * @param email
     * @param mdp
     * @return le token de connexion
     * @throws ExceptionMauvaisIdentifiants
     */
    public String connexion(String email,String mdp) throws ExceptionMauvaisIdentifiants;

    /**
     * Méthode qui permet de se déconnecter
     * @param token
     * @return true si déconnexion réussi false sinon
     * @throws ExceptionMauvaisIdentifiants
     */
    public boolean deconnexion(String token) throws ExceptionMauvaisIdentifiants;
}
