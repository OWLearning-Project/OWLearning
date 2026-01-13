package Domain.Models;
import java.util.ArrayList;
import Shared.Exceptions.ExceptionsDiscussion;
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

    public void ajouterMessage(Message message) throws ExceptionsDiscussion
    {
        if(message == null)
        {
            throw new ExceptionsDiscussion(ExceptionsDiscussion.messageNull);
        }
        Utilisateur Auteur = message.getAuteur();
        if (!utilisateurFaitParti(Auteur.getId()))
        {
            throw new ExceptionsDiscussion(ExceptionsDiscussion.utilisateurNonAutorise);
        }

        this.messages.add(message);
        message.setDiscussion(this);
    }

    public boolean utilisateurFaitParti(int id)
    {
        boolean faitParti = false;
        for (int i = 0; i<this.participants.size(); i++)
        {
            if(this.participants.get(i).getId() == id)
            {
                faitParti = true;
            }
        }
        return faitParti;
    }

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
