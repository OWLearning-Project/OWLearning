package app.OwLearning.Domain.Ports.IRepository;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;

public interface IChapitreRepository {

    /**
     * Sauvegarde un chapitre dans la bd
     * @param chapitre a sauvgaerder
     * @return ligne a la quelle le chapitre est inscrit dans la bd
     */
    public int sauvegarder(Chapitre chapitre);

    /**
     * Recherche un chapitre par son id
     * @param id du chapitre
     * @return chapitre si trouvé. Sinon null si non trouvé.
     */
    public Chapitre trouverParId(int id);

    /**
     * supprimé le chapitre
     * @param id du chapitre
     * @return le chapitre qui a été supprimé
     */
    public Chapitre supprimerParId(int id);

    /**
     * verification de l'existance du chapitre
     * @param i id du chapitre
     * @return True si le chapitre existe, sinon false si il n'existe pas
     */
    public boolean existe(int i);

}
