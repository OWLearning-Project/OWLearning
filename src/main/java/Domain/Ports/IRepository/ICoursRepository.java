package Domain.Ports.IRepository;

import Domain.Models.Cours;
import java.util.ArrayList;

/**
 * Interface ICoursRepository définissant le contrat pour la récupération des cours
 */
public interface ICoursRepository
{
    /**
     * Méthode pour trouver les cours publiés avec ou sans filtre
     * @return l'ArrayList des Cours publiés
     */
    public abstract ArrayList<Cours> trouverCoursFiltre(String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive);

    /**
     * Méthode pour trouver un cours par son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public abstract Cours trouverParId(int id);

    /**
     * Méthode pour trouver les cours créés par un créateur via son id
     * @param createurId id du créateur
     * @return l'ArrayList des cours créés
     */
    public abstract ArrayList<Cours> trouverParIdCreateur(String titre, int createurId, String difficulte, String categorie, Boolean estPrive);

    /**
     * Méthode pour trouver les cours où un élève est inscrit via son id
     * @param eleveId id de l'élève
     * @return l'ArrayList des cours inscrits
     */
    public abstract ArrayList<Cours> trouverParIdEleve(int eleveId, String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive);

    /**
     * Cette méthode crée un nouveau cours après vérification des données
     * @param titre titre du cours
     * @param description description du cours
     * @param categorie catégorie du cours
     * @param createurId id du créateur
     * @return le cours créé
     */
    public abstract Cours creerCours(String titre, String description, String categorie, int createurId);

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

}
