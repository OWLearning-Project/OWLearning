package app.OwLearning.Shared.DTO;

public class MessageEnvoiDTO
{
    private int auteurId;
    private String contenu;
    private int ressourceId;

    public MessageEnvoiDTO()
    {}

    public MessageEnvoiDTO(int auteurId, String contenu,  int ressourceId)
    {
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.ressourceId = ressourceId;
    }

    public int getAuteurId()
    {
        return this.auteurId;
    }

    public String getContenu()
    {
        return this.contenu;
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

