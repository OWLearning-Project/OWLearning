package app.OwLearning.Domain.Ports.IServices;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;

/**
 * Interface IServiceChapitre définissant le contrat pour le traitement des chapitres
 */
public interface IServiceChapitre {
    /**
     * Méthode pour récupérer le contenu d'un chapitre via son id
     * @param id id du chapitre
     * @return l'objet Chapitre associé
     */
    public abstract Chapitre getContenuChapitre(int id);

    /**
     * Ajouter une ressource au chapitre sélectionné
     * @param id du chapitre
     * @param ressource
     */
    public abstract void ajouterRessource(int id, Ressource ressource);

    /**
     * Permet de changer de titre ou de description pour un chapitre
     *
     * @return 1 si le chapitre est trouvé et changé. Sinon 0 s'il n'existe pas
     */
    public abstract void modifier(int id, String titre, String description);

    /**
     * Détache la ressource du chapitre
     *
     * @param idChapitre
     * @param idRessource
     * @return
     */
    public abstract Ressource retirerRessource(int idChapitre, int idRessource);
}
