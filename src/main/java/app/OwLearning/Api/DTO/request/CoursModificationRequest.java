package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domain.Models.Difficulte;

/**
 * DTO de requête pour la modification d'un cours
 */
public class CoursModificationRequest
{
    private String titre;
    private String description;
    private Difficulte difficulte;
    private boolean estPrive;

    public CoursModificationRequest() {}

    public CoursModificationRequest(String titre,
                                    String description,
                                    Difficulte difficulte,
                                    boolean estPrive)
    {
        this.titre = titre;
        this.description = description;
        this.difficulte = difficulte;
        this.estPrive = estPrive;
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

    public boolean getEstPrive()
    {
        return estPrive;
    }
}
