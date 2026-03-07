package app.OwLearning.Domain.Ports.IServices;

import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Models.TypeRessource;

public interface IServiceRessource {

    public Ressource getContenuRessource(int id);

    public Ressource creeRessource(String nom, String url, TypeRessource type);

    public Ressource supprimerRessource(int id);

    public void modifier(int id, String nom, String url, TypeRessource type);
}
