package app.OwLearning.Api.DTO.request;

/**
 * DTO de requête pour la création ou modification d'un chapitre
 */
public class ChapitreRequest
{
    private String titre;
    private String description;

    public ChapitreRequest() {}

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}