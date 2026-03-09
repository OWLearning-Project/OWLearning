package app.OwLearning.Shared.DTO;

import app.OwLearning.Domain.Models.TypeRessource;

public class RessourceDTO {
    private String nom;
    private String url;
    private TypeRessource type;

    public RessourceDTO(){}

    public String getNom(){
        return this.nom;
    }

    public void SetNom(String unNom){
        this.nom = unNom;
    }

    public String getUrl(){
        return  this.url;
    }

    public void setUrl(String unUrl){
        this.url = unUrl;
    }

    public TypeRessource getType(){
        return this.type;
    }

    public void setType(TypeRessource unType){
        this.type = unType;
    }
}
