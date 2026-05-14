package app.OwLearning.Api.DTO.request;

/**
 * DTO de requête pour l'envoi d'un message
 */
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

    public int getAuteurId()
    {
        return auteurId;
    }

    public String getContenu()
    {
        return contenu;
    }

    public Integer getRessourceId()
    {
        return ressourceId;
    }

    public void setContenu(String contenu)
    {
        this.contenu = contenu;
    }

    public void setRessourceId(Integer ressourceId)
    {
        this.ressourceId = ressourceId;
    }
}
