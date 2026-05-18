package app.OwLearning.Domaine.Interfaces;


import app.OwLearning.Domaine.Entités.Chapitre;

/**
 * Interface IChapitreRepository définissant le contrat pour la récupération des chapitres
 */
public interface IChapitreRepository {

    /**
     * Sauvegarde un chapitre dans la bd
     * @param chapitre à sauvegarder
     * @return ligne à laquelle le chapitre est inscrit dans la bd
     */
    public abstract int sauvegarder(Chapitre chapitre);

    /**
     * Recherche un chapitre par son id
     * @param id du chapitre
     * @return chapitre si trouvé. Sinon null si non trouvé.
     */
    public abstract Chapitre trouverParId(int id);

    /**
     * supprime le chapitre
     * @param id du chapitre
     * @return le chapitre qui a été supprimé
     */
    public abstract Chapitre supprimerParId(int id);

    /**
     * Vérification de l'existence du chapitre
     * @param i id du chapitre
     * @return True si le chapitre existe, sinon false s'il n'existe pas
     */
    public abstract boolean existe(int i);

}
