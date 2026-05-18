package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domaine.Enumérations.TypeRessource;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour l'ajout d'une ressource
 */
@Getter
@Setter
public class AjoutRessourceRequest
{
    private String nom;
    private String url;
    private TypeRessource type;

    public AjoutRessourceRequest() {}


}
