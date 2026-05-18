package app.OwLearning.Api.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ChapitreResponse {
    private int id;
    private String titre;
    private String description;
    private List<RessourceResponse> ressources;

    public ChapitreResponse() {}

}
