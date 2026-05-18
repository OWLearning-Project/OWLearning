package app.OwLearning.Api.DTO.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour la création ou modification d'un chapitre
 */
@Getter
@Setter
public class ChapitreRequest
{
    private String titre;
    private String description;

    public ChapitreRequest() {}


}