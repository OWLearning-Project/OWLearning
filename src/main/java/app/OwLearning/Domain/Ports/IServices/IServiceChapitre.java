package app.OwLearning.Domain.Ports.IServices;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;

public interface IServiceChapitre {
    public Chapitre getContenuChapitre(int id);

    public void ajouterRessource(int id, Ressource ressource);

    public Ressource retirerRessource(int idChapitre, int idRessource);
}
