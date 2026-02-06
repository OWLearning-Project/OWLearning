package Domain.Ports.IServices;

import Domain.Models.Cours;

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
}
