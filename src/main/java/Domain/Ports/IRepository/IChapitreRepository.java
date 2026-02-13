package Domain.Ports.IRepository;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;

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
     * Permet de changer de titre ou de description pour un chapitre
     *
     * @param chapitre à modifier
     * @return Nombre de ligne modifier
     */
    public int modifier(Chapitre chapitre);

    /**
     * Ajout une ressource au chapitre selectionné
     * @param id du chapitre
     * @param ressource
     */
    public int ajouterRessourceAuChapitre(int id, Ressource ressource);

    /**
     * Supprime la ressource de tout les chapitres
     * @param id
     */
    //public void supprimerRessourceAuChapitre(int id);

    /**
     * Détache la ressource du chapitre
     *
     * @param idChapitre
     * @param idRessource
     * @return Nombre le ligne supprimé
     */
    public Ressource retirerRessourceDuChapitre(int idChapitre, int idRessource);

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
