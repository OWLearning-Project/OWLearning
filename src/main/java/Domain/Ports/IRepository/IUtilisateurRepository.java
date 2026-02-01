package Domain.Ports.IRepository;

import Domain.Models.Utilisateur;

public interface IUtilisateurRepository
{
    // Pour avoir un Utilisateur a partir de son email
    public Utilisateur trouverParEmail(String email);

    // Mettre à jour la dernière connexion / Sauvegarder son inscription
    public int sauvegarder(Utilisateur utilisateur);
}
