package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domain.Models.StatutMessage;

import java.sql.Timestamp;
import java.util.List;

public class MessageResponse {
    private int id;
    private String contenu;
    private Timestamp dateCreation;
    private StatutMessage statutMessage;
    private int idDiscussion;
    private int idUtilisateur;
    private List<RessourceResponse> ressources;

    public MessageResponse() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Timestamp getDateCreation() { return dateCreation; }
    public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }

    public StatutMessage getStatutMessage() { return statutMessage; }
    public void setStatutMessage(StatutMessage statutMessage) { this.statutMessage = statutMessage; }

    public int getIdDiscussion() { return idDiscussion; }
    public void setIdDiscussion(int idDiscussion) { this.idDiscussion = idDiscussion; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public List<RessourceResponse> getRessources() { return ressources; }
    public void setRessources(List<RessourceResponse> ressources) { this.ressources = ressources; }
}
