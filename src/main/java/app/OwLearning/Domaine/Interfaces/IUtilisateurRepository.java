package app.OwLearning.Domaine.Interfaces;

import app.OwLearning.Domaine.Entités.Utilisateur;

import java.util.List;

/**
 * Interface IUtilisateurRepository définissant le contrat pour la récupération des utilisateurs
 */
public interface IUtilisateurRepository
{
    /**
     * Méthode qui permet de trouver l'utilisateur via son email
     * @param email
     * @return l'Utilisateur trouvé
     */
    public abstract Utilisateur trouverParEmail(String email);

    /**
     * Méthode qui permet de sauvegarder un utilisateur
     * @param utilisateur
     * @return l'utilisateur sauvegardé
     */
    public abstract Utilisateur sauvegarder(Utilisateur utilisateur);

    /**
     * Cette méthode permet de trouver un utilisateur via son identifiant
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    public abstract Utilisateur trouverParId(int id);

    /**
     * Methode qui permet de récupérer les créateurs.
     * @return la liste des créateurs
     */
    public abstract List<Utilisateur> trouverCreateurs();

    /**
     * Cette methode retourne une liste de tous les utilisateur
     * @return
     */
    public abstract List<Utilisateur> findAll();
}
