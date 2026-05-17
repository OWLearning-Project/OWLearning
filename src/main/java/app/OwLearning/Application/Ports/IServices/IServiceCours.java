package app.OwLearning.Application.Ports.IServices;

import app.OwLearning.Domain.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Domain.Exceptions.ExceptionMauvaisLabelCategorie;
import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Difficulte;

import java.util.ArrayList;

/**
 * Interface IServiceCours définissant le contrat pour le traitement des cours
 */
public interface IServiceCours
{
    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * @return l'ArrayList des Cours publiés
     */
    public abstract ArrayList<Cours> getCoursPublies();

    /**
     * Méthode permettant de récupérer un cours avec son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public abstract Cours getCoursParId(int id);

    /**
     * Méthode qui permet de récupérer les cours créés par un créateur avec son id
     * @param idCreateur id du créateur
     * @return l'ArrayList des Cours créés
     */
    public abstract ArrayList<Cours> getCoursCrees(int idCreateur);

    /**
     * Méthode permettant de récupérer les Cours auxquels un élève est inscrit avec son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public abstract ArrayList<Cours> getCoursInscrits(int idEleve);

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
    public abstract void modifierInformationsCours(int coursId, String titre, String description, Difficulte difficulte, boolean estPrive);


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
    public abstract void ajouterChapitre(int coursId, Chapitre chapitre);

    /**
     * Methode permettant de retirer un chapitre à un cours
     * @param coursId id du cours
     * @param ChapitreId id du chapitre
     * @return l'objet chapitre qui est retirer
     */
    public abstract void retirerChapitre(int coursId, int ChapitreId) throws ExceptionMauvaisIdChapitre;

    /**
     * Methode permettant d'ajouter une categorie à un cours
     * @param coursId id du cours
     * @param categorie categorie à ajouter au cours
     */
    public abstract void ajouterCategorieCours(int coursId, Categorie categorie);

    /**
     * Methode permettant de supprimer une categorie d'un cours
     * @param coursId id du cours
     * @param categorie categorie a supprimer
     * @return la categorie qui est retirer du cours
     */
    public abstract void supprimerCategorieCours(int coursId, Categorie categorie) throws ExceptionMauvaisLabelCategorie;
}
