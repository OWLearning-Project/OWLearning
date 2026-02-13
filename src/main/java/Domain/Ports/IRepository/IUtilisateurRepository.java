package Domain.Ports.IRepository;

import Domain.Models.Utilisateur;

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
     * Méthode qui permet de trouver l'utilisateur via son id
     * @param id identifiant de user
     * @return l'Utilisateur trouvé
     */
    public Utilisateur trouverParId(int id);

    /**
     * Méthode qui permet de mettre à jour un Utilisateur
     * @param utilisateur l'utilisateur
     * @return le nombre de lignes modifiées
     */
    public int mettreAJour(Utilisateur utilisateur);

}