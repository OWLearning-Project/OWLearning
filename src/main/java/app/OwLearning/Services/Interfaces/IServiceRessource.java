package app.OwLearning.Services.Interfaces;

import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Enumérations.TypeRessource;

public interface IServiceRessource {

    public Ressource getContenuRessource(int id);

    public Ressource creeRessource(String nom, String url, TypeRessource type);

    public Ressource supprimerRessource(int id);

    public void modifier(int id, String nom, String url, TypeRessource type);
}
