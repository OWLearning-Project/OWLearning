package app.OwLearning.Api.DTO.response;

import java.util.List;

public class ChapitreResponse {
    private int id;
    private String titre;
    private String description;
    private List<RessourceResponse> ressources;

    public ChapitreResponse() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<RessourceResponse> getRessources() { return ressources; }
public void setRessources(List<RessourceResponse> ressources) { this.ressources = ressources; }
}
