package Domain.Ports.IRepository;

import Domain.Models.Utilisateur;

public interface IUtilisateurRepository
{
    // Verifie si un compte existe déjà avant d'essayer de le créer
    boolean emailExistant(String email);

    // Pour avoir un Utilisateur a partir de son email
    Utilisateur trouverParEmail(String email);

    // Mettre à jour la dernière connexion / Sauvegarder son inscription
    Utilisateur sauvegarder(Utilisateur utilisateur);

    // Les commentaires seront à changés mais je t'ai mis ça pour savoir comment les utiliser
}
