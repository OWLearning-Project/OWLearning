package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domain.Models.TypeRessource;

public class RessourceResponse {
    private int id;
    private String nom;
    private String url;
    private TypeRessource type;

    public RessourceResponse() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public TypeRessource getType() { return type; }
    public void setType(TypeRessource type) { this.type = type; }
}
