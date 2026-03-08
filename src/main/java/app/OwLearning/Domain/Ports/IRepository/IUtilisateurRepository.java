package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Utilisateur;

import java.util.ArrayList;

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
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    public  Utilisateur trouverParId(int id);

    public int mettreAJour(Utilisateur utilisateur);

    //Ajout methode pour ServiceInscription
    /**
     * Méthode qui récupére les élèves inscrits à un cours
     *
     * @param idCours id du cours
     * @return la liste des utilisateurs inscrits
     */
    public abstract ArrayList<Utilisateur> trouverEtudiantsInscrits(int idCours);
}
