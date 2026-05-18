package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domain.Models.TypeRessource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RessourceResponse {
    private int id;
    private String nom;
    private String url;
    private TypeRessource type;

    public RessourceResponse() {}
}
