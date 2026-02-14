package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Utilisateur;

/**
 * Interface de UtilisateurRepository qui permet de trouver un Utilisateur grâce à son email et de sauvegarder un Utilisateur
 */
public interface IUtilisateurRepository
{
    /**
     * Méthode qui permet de trouver l'utilisateur via son email
     * @param email
     * @return l'Utilisateur trouvé
     */
    public Utilisateur trouverParEmail(String email);

    /**
     * Méthode qui permet de sauvegarder un Utilisateur
     * @param utilisateur
     * @return l'utilisateur sauvegardé
     */
    public Utilisateur sauvegarder(Utilisateur utilisateur);

    /**
     * Cette méthode permet de trouver un utilisateur via son identifiant
     * @param id identifiant de user
     * @return l'utilisateur trouvé
     */
    public  Utilisateur trouverParId(int id);

    /**
     * cette méthode permet de mettre à jour les infos d'un utilisateur
     * @param utilisateur l'utilisateur
     * @return les données mises à jour
     */
    public int mettreAJour(Utilisateur utilisateur);
}
