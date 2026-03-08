package app.OwLearning.Shared.DTO;

import app.OwLearning.Domain.Models.TypeRessource;

/**
 * DTO permettant l'ajout de ressource
 */
public class AjoutRessourceDTO
{
    private String nom;
    private String url;
    private TypeRessource type;

    public AjoutRessourceDTO() {}

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
