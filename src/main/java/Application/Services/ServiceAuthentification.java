package Application.Services;

import Domain.Models.Createur;
import Domain.Models.Eleve;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Domain.Ports.IServices.IHach;
import Domain.Ports.IServices.IServiceToken;
import Shared.Exceptions.ExceptionCompteExistant;

import Shared.Exceptions.ExceptionMauvaisIdentifiants;
import Shared.Exceptions.ExceptionTokenInvalide;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Classe ServiceAuthentification qui permet d'inscrire de connecter ou de déconnecter un utilisateur
 */
public class ServiceAuthentification
{
    private final IUtilisateurRepository utilisateurRepository;
    private final IHach hach;
    private final IServiceToken serviceToken;

    /**
     * Constructeur de ServiceAuthentification
     * @param utilisateurRepository
     * @param hach
     * @param serviceToken
     */
    public ServiceAuthentification(IUtilisateurRepository utilisateurRepository, IHach hach, IServiceToken serviceToken) // Utilisation des Interfaces pour ne pas dépendre de l'implémentation concrète
    {
        this.utilisateurRepository = utilisateurRepository;
        this.hach = hach;
        this.serviceToken = serviceToken;
    }

    /**
     * Méthode qui permet d'inscrire un utilisateur
     * @param nom
     * @param prenom
     * @param email
     * @param mdp
     * @param role
     * @throws ExceptionCompteExistant
     */
    public void inscription(String nom, String prenom, String email, String mdp, String role) throws ExceptionCompteExistant
    {
        Utilisateur utilisateur;
        String mdpHache = hach.hacher(mdp);

        if (utilisateurRepository.trouverParEmail(email) != null)
            throw new ExceptionCompteExistant("Un compte existe déjà", email);

        if (role.equalsIgnoreCase("createur"))
        {
            utilisateur = new Createur(nom, prenom, email, mdpHache);
        }
        else if (role.equalsIgnoreCase("eleve"))
        {
            utilisateur = new Eleve(nom, prenom, email, mdpHache);
        }
        else
        {
            throw new IllegalArgumentException("rôle inexistant");
        }
        utilisateurRepository.sauvegarder(utilisateur);
    }

    /**
     * Méthode qui permet de connecter un utilisateur
     * @param email
     * @param mdp
     * @return le token de connexion
     * @throws ExceptionMauvaisIdentifiants
     */
    public String connexion(String email, String mdp) throws ExceptionMauvaisIdentifiants
    {
        Utilisateur utilisateur = utilisateurRepository.trouverParEmail(email);
        if (utilisateur == null)
        {
            throw new ExceptionMauvaisIdentifiants("Identifiants incorrects", email);
        }
        if (!hach.valider(mdp, utilisateur.getMotDePasse()))
        {
            throw new ExceptionMauvaisIdentifiants("Identifiants incorrecte", email);
        }

        utilisateur.setDerniereActivite(Timestamp.from(Instant.now()));
        utilisateurRepository.sauvegarder(utilisateur);

        return serviceToken.genererToken(utilisateur);
    }

    /**
     * Méthode qui permet de déconnecter un utilisateur
     * @param token
     * @throws ExceptionMauvaisIdentifiants
     */
    public void deconnexion(String token)
    {
        if (token == null || token.isEmpty())
        {
            throw new ExceptionTokenInvalide("Impossible de se déconnecter","token vide");
        }
        serviceToken.invaliderToken(token);

    }
}
