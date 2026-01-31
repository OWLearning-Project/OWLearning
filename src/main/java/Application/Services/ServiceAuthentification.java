package Application.Services;

import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Domain.Ports.IServices.IHach;
import Domain.Ports.IServices.IServiceToken;
import Shared.Exceptions.ExceptionMauvaisIdentifiants;

import java.sql.Timestamp;
import java.time.Instant;

public class ServiceAuthentification
{
    private final IUtilisateurRepository utilisateurRepository;
    private final IHach hach;
    private final IServiceToken serviceToken;

    public ServiceAuthentification(IUtilisateurRepository utilisateurRepository, IHach hach, IServiceToken serviceToken)
        {
        this.utilisateurRepository = utilisateurRepository;
        this.hach = hach;
        this.serviceToken = serviceToken;
        }

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

    public boolean deconnexion(String token) throws ExceptionMauvaisIdentifiants
    {
        if (token == null || token.isEmpty()) {
            return false;
        }

        serviceToken.invaliderToken(token);

        return true;
    }
}
