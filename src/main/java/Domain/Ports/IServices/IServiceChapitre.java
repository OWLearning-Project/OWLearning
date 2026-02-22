package Domain.Ports.IServices;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;

public interface IServiceChapitre {
    public Chapitre getContenuChapitre(int id);

    public void ajouterRessource(int id, Ressource ressource);

    public void modifier(int id, String titre, String description);

    public Ressource retirerRessource(int idChapitre, int idRessource);
}
