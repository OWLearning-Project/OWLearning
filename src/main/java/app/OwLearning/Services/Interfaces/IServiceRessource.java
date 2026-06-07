package app.OwLearning.Services.Interfaces;

import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
import org.springframework.web.multipart.MultipartFile;

public interface IServiceRessource {

    public Ressource getContenuRessource(int id);

    public Ressource creeRessource(String nom, String url, TypeRessource type);

    public Ressource uploaderRessource(MultipartFile fichier, String urlBase);

    public Ressource supprimerRessource(int id);

    public void modifier(int id, String nom, String url, TypeRessource type);
}
