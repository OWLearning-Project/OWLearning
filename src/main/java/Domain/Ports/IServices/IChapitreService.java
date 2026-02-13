package Domain.Ports.IServices;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;

public interface IChapitreService {
    public Chapitre getContenuChapitre(int id);

    public void ajouterRessource(int id, Ressource ressource);

    //Pas sur de la laisser ici peut etre dans un service ressource ?
    public Ressource supprimerRessource(int idRessource);

    public void retirerRessource(int idChapitre, int idRessource);
}
