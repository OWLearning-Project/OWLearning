package Domain.Models;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
public class Discussion 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_discussion")
    private int idDiscussion;
    private ArrayList<Utilisateur> participants;
    private ArrayList<Message> messages;

    public Discussion(Utilisateur utilisateur1, Utilisateur utilisateur2) {
        this.participants = new ArrayList<Utilisateur>();
        this.participants.add(utilisateur1);
        this.participants.add(utilisateur2);
        this.messages = new ArrayList<Message>();
    }

    public void ajouterMessage(int auteurId, String contenu){}

    public boolean utilisateurFaitParti(int id){ return false;}

    public int getId() {
        return this.idDiscussion;
    }

    public ArrayList<Utilisateur> getParticipants() 
    {
        return this.participants;
    }

    public void setParticipants(ArrayList<Utilisateur> desParticipants) 
    {
        this.participants = desParticipants;
    }

    public ArrayList<Message> getMessages() 
    {
        return this.messages;
    }
    
    public void setMessages(ArrayList<Message> desMessages) 
    {
        this.messages = desMessages;
    }
}
