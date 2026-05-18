package app.OwLearning.Api.DTO.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour l'envoi d'un message
 */
@Getter
@Setter
public class MessageEnvoiRequest
{
    private int auteurId;
    private String contenu;
    private Integer ressourceId;

    public MessageEnvoiRequest() {}

    public MessageEnvoiRequest(int auteurId,
                               String contenu,
                               Integer ressourceId)
    {
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.ressourceId = ressourceId;
    }

}
