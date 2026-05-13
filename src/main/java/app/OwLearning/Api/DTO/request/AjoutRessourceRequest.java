package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domain.Models.TypeRessource;

/**
 * DTO de requête pour l'ajout d'une ressource
 */
public class AjoutRessourceRequest
{
    private String nom;
    private String url;
    private TypeRessource type;

    public AjoutRessourceRequest() {}

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public TypeRessource getType()
    {
        return type;
    }

    public void setType(TypeRessource type)
    {
        this.type = type;
    }
}
