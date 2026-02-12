package Domain.Ports.IRepository;

import Domain.Models.Utilisateur;

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
    // trouver le user par son Id
    public Utilisateur trouverParId(int id);
    /**
     * Méthode qui permet de sauvegarder un Utilisateur
     * @param utilisateur
     * @return le nombre de ligne sauvegarder
     */
    public int sauvegarder(Utilisateur utilisateur);

    public int sauvegarderCreateur(int id);

    public int sauvegarderEleve(int id);

    public int trouverIdParEmail(String email);

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