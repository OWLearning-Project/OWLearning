package Domain.Ports.IRepository;

import Domain.Models.Chapitre;

import java.util.List;

public interface ChapitreRepository {

    /**
     * Sauvegarde un chapitre dans la bd
     * @param chapitre a sauvgaerder
     * @return ligne a la quelle le chapitre est inscrit dans la bd
     */
    public int sauvegarder(Chapitre chapitre);

    /**
     * Recherche un chapitre par son id
     * @param i id du chapitre
     * @return chapitre si trouvé. Sinon null si non trouvé.
     */
    public Chapitre trouverParId(int i);

    /**
     * Recherche tout les chapitres de la bd
     * @return list des chapitres, ou null si pas de chapitre trouvé.
     */
    public List<Chapitre> trouverTout();

    /**
     * supprimé le chapitre
     * @param i id du chapitre
     * @return le chapitre qui a été supprimé
     */
    public Chapitre supprimerParId(int i);

    /**
     * verification
     * @param i id du chapitre
     * @return True si le chapitre existe, sinon false si il n'existe pas
     */
    public boolean existe(int i);

}
