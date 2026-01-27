package Application.Services;

import Domain.Models.Createur;
import Domain.Models.Eleve;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Domain.Ports.IServices.IHach;
import Domain.Ports.IServices.IServiceToken;
import Shared.Exceptions.ExceptionCompteExistant;


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

    public boolean inscription(String nom, String prenom, String email, String mdp, int age, String niveauEtude, String role) throws ExceptionCompteExistant
    {
        Utilisateur utilisateur;

        if (utilisateurRepository.trouverParEmail(email) != null)
            throw new ExceptionCompteExistant("Un compte existe déjà", email);

        if (role.equalsIgnoreCase("createur"))
            utilisateur = new Createur(nom, prenom, email, mdp);

        else if (role.equalsIgnoreCase("eleve"))
            utilisateur = new Eleve(nom, prenom, email, mdp, age, niveauEtude);

        else
            throw new IllegalArgumentException("rôle inexistant");

        utilisateur.setMotDePasse(hach.hacher(mdp));
        Utilisateur utilisateurInsere = utilisateurRepository.sauvegarder(utilisateur);

        return utilisateurInsere != null;
    }

}