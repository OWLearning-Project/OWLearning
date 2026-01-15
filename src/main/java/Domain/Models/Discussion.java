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
        Utilisateur Auteur = message.getUtilisateur();
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

    private String toStringParticipants()
    {
        String s="";
        for (int i = 0; i<this.participants.size(); i++)
        {
            s += " " + this.participants.get(i).toString()+"\n";
        }
        return "Participants : \n[\n" + s + "]\n";
    }

    private String  toStringMessages()
    {
        String s="";
        for (int i = 0; i<this.messages.size(); i++)
        {
            s += " " + this.messages.get(i).toString()+"\n";
        }
        return "Messages : \n[\n" + s + "]\n";
    }

    public String toString()
    {
        return this.toStringParticipants() + "\n" + this.toStringMessages();
    }

}
