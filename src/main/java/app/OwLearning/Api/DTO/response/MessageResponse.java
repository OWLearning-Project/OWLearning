package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domaine.Enumérations.StatutMessage;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class MessageResponse {
    private int id;
    private String contenu;
    private Timestamp dateCreation;
    private StatutMessage statutMessage;
    private int idDiscussion;
    private int idUtilisateur;
    private List<RessourceResponse> ressources;

    public MessageResponse() {}

}
