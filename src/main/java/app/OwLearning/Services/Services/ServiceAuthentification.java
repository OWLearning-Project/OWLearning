package app.OwLearning.Services.Services;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import app.OwLearning.Services.Interfaces.IHach;
import app.OwLearning.Services.Interfaces.IServiceToken;
import app.OwLearning.Services.Exceptions.ExceptionCompteExistant;

import app.OwLearning.Services.Exceptions.ExceptionMauvaisIdentifiants;
import app.OwLearning.Services.Exceptions.ExceptionTokenInvalide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Classe ServiceAuthentification qui permet de gérer les traitements d'authentification
 */
@Slf4j
@Service
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
        log.debug("Demande d'inscription: nom='{}', prenom='{}', email='{}', role='{}'", nom, prenom, email ,role);
        Utilisateur utilisateur;
        String mdpHache = hach.hacher(mdp);

        if (utilisateurRepository.trouverParEmail(email) != null){
            log.warn("Echec de l'inscription: l'email '{}' est déja utilisé", email);
            throw new ExceptionCompteExistant("Un compte existe déjà", email);
        }


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
            log.warn("Echec de l'inscription le role '{}' n'existe pas", role);
            throw new IllegalArgumentException("rôle inexistant");
        }
        utilisateurRepository.sauvegarder(utilisateur);
        log.info("Nouvel utilisateur inscrit: email='{}', role='{}'", email, role);
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
        log.debug("Demande de connexion: email='{}'", email);
        Utilisateur utilisateur = utilisateurRepository.trouverParEmail(email);
        if (utilisateur == null)
        {
            log.warn("Echec de la connexion: l'email '{}' est introuvable", email);
            throw new ExceptionMauvaisIdentifiants("Identifiants incorrects", email);
        }
        if (!hach.valider(mdp, utilisateur.getMotDePasseHash()))
        {
            log.warn("Echec de la connexion: mot de passe incorrect pour l'email '{}'", email);
            throw new ExceptionMauvaisIdentifiants("Identifiants incorrecte", email);
        }

        utilisateur.setDerniereActivite(Timestamp.from(Instant.now()));
        utilisateurRepository.sauvegarder(utilisateur);

        log.info("Connexion de l'utilisateur: email='{}'", email);
        return serviceToken.genererToken(utilisateur);
    }

    /**
     * Méthode qui permet de déconnecter un utilisateur
     * @param token
     * @throws ExceptionMauvaisIdentifiants
     */
    public void deconnexion(String token)
    {
        log.debug("Demande de deconnexion");
        if (token == null || token.isEmpty())
        {
            log.warn("Echec de la deconnexion: token vide ou null");
            throw new ExceptionTokenInvalide("Impossible de se déconnecter","token vide");
        }
        serviceToken.invaliderToken(token);
        log.info("Deconnexion: le token a été invalidé");

    }
}
