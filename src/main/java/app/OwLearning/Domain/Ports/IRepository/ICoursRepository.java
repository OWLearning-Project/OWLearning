package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.*;
import java.util.ArrayList;
import java.util.Optional;

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
    public Chapitre retirerChapitre(int coursId, int ChapitreId) throws ExceptionMauvaisIdChapitre;

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
    public Categorie supprimerCategorieCours(int coursId, Categorie categorie) throws ExceptionMauvaisLabelCategorie;

    boolean coursExiste(int coursId);

    void sauvegarder(Cours cours);
}
