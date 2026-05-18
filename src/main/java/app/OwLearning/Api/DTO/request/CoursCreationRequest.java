package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domaine.Enumérations.Difficulte;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour la création d'un cours
 */
@Getter
@Setter
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

}
