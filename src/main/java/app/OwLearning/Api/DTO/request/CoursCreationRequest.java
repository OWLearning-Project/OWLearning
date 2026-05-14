package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domain.Models.Difficulte;

/**
 * DTO de requête pour la création d'un cours
 */
public class CoursCreationRequest
{
    private String titre;
    private String description;
    private Difficulte difficulte;

    public CoursCreationRequest() {}

    public CoursCreationRequest(String titre, String description, Difficulte difficulte)
    {
        this.titre = titre;
        this.description = description;
        this.difficulte = difficulte;
    }

    public String getTitre()
    {
        return titre;
    }

    public String getDescription()
    {
        return description;
    }

    public Difficulte getDifficulte()
    {
        return difficulte;
    }
}
