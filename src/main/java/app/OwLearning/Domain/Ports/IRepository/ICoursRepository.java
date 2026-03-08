package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.*;
import java.util.ArrayList;

/**
 * Interface ICoursRepository définissant le contrat pour la récupération des cours
 */
public interface ICoursRepository
{
    /**
     * Méthode pour trouver les cours publiés
     * @return l'ArrayList des Cours publiés
     */
    public abstract ArrayList<Cours> trouverCoursPublies();

    /**
     * Méthode pour trouver un cours par son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public abstract Cours trouverParId(int id);

    /**
     * Méthode pour trouver les cours créés par un créateur via son id
     * @param idCreateur id du créateur
     * @return l'ArrayList des cours créés
     */
    public abstract ArrayList<Cours> trouverParIdCreateur(int idCreateur);

    /**
     * Méthode pour trouver les cours auxquels un élève est inscrit via son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des cours inscrits
     */
    public abstract ArrayList<Cours> trouverParIdEleve(int idEleve);

    /**
     * Cette méthode crée un nouveau cours après vérification des données
     * @param titre titre du cours
     * @param description description du cours
     * @param difficulte difficulté du cours
     * @param createurId id du créateur
     * @return le cours créé
     */
    public abstract Cours creerCours(String titre, String description, Difficulte difficulte, int createurId);
    /**
     * Publier un cours
     * @param coursId id du cours à publier
     */
    public abstract void publierCours(int coursId);

    /**
     * Modifie le titre et la description d’un cours
     * @param coursId id du cours
     * @param titre nouveau titre du cours
     * @param description nouvelle description du cours
     */
    public abstract void modifierInformationsCours(int coursId, String titre, String description);

    /**
     * Cette methode change le statut privé ou public d’un cours
     * @param coursId id du cours
     * @param estPrive nouveau statut du cours
     */
    public abstract void coursPrive(int coursId, boolean estPrive);

    /**
     * Methode permettant de supprimer un cours
     * @param coursId id du cours
     * @return l'objet Cours supprimé
     */
    public abstract Cours supprimerCours(int coursId);

    public abstract boolean coursExiste(int coursId);

    /**
     * cette méthode nous  permet d'inscrire un étudiant (élève) à un cours
     *
     * @param idEtudiant id de l'élève
     * @param idCours id du cours
     * @return un entier indiquant le résultat de l'inscription
     */
    public abstract int inscrireEtudiant(int idEtudiant, int idCours);

    /**
     * Valider l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    public abstract void validerInscription(int idCours, int idEtudiant);

    /**
     * Refuser l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    public abstract void refuserInscription(int idCours, int idEtudiant);

    public abstract void sauvegarder(Cours cours);
}
