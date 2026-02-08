package Domain.Ports.IServices;

import Domain.Models.Categorie;
import Domain.Models.Chapitre;
import Domain.Models.Cours;
import Domain.Models.Difficulte;

import java.util.ArrayList;

/**
 * Interface IServiceCours définissant le contrat pour le traitement des cours
 */
public interface IServiceCours
{
    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * Filtrage possible avec les paramètres
     * @return l'ArrayList des Cours publiés
     */
    public abstract ArrayList<Cours> getLesCours(String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive);

    /**
     * Méthode permettant de récupérer un cours avec son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public abstract Cours getCoursParId(int id);

    /**
     * Méthode qui permet de récupérer les cours créés par un créateur avec son id
     * Filtrage possible avec les paramètres
     * @param idCreateur id du créateur
     * @return l'ArrayList des Cours créés
     */
    public abstract ArrayList<Cours> getCoursCrees(String titre, int idCreateur, String difficulte, String categorie, Boolean estPrive);

    /**
     * Méthode permettant de récupérer les Cours où un élève est inscrit avec son id
     * Filtrage possible avec les paramètres
     * @param eleveId id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public abstract ArrayList<Cours> getCoursInscrits(int eleveId, String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive);

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

    /**
     * Methode permettant d'ajouter un chapitre à un cours
     * @param coursId id du cours
     * @param chapitre chapitre à ajouter
     */
    public void ajouterChapitre(int coursId, Chapitre chapitre);

    /**
     * Methode permettant de retirer un chapitre à un cours
     * @param coursId id du cours
     * @param ChapitreId id du chapitre
     * @return l'objet chapitre qui est retirer
     */
    public Chapitre retirerChapitre(int coursId, int ChapitreId);

    /**
     * Methode qui permet la modification d'une difficulté d'un cours
     * @param coursId id du cours
     * @param difficulte la difficulté à modifier
     */
    public void modifierDifficulteCours(int coursId, Difficulte difficulte);

    /**
     * Methode permettant d'ajouter une categorie à un cours
     * @param coursId id du cours
     * @param categorie categorie à ajouter au cours
     */
    public void ajouterCategorieCours(int coursId, Categorie categorie);

    /**
     * Methode permettant de supprimer une categorie d'un cours
     * @param coursId id du cours
     * @param categorie categorie a supprimer
     * @return la categorie qui est retirer du cours
     */
    public Categorie supprimerCategorieCours(int coursId, Categorie categorie);
}
