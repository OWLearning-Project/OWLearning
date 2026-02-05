package Domain.Models;
import java.util.ArrayList;

import Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import jakarta.persistence.*;

/**
 * Classe Discussion qui permet de créer une discussion en gérant ses participants et les messages de la discussion
 */
@Entity
public class Discussion 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_discussion")
    private int idDiscussion;
    @ManyToMany
    @JoinTable(
            name = "participation_discussion",
            joinColumns = @JoinColumn(name = "id_discussion"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur")
    )
    private ArrayList<Utilisateur> participants;
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Message> messages;

    /**
     * Constructeur de Discussion
     * @param utilisateur1
     * @param utilisateur2
     */
    public Discussion(Utilisateur utilisateur1, Utilisateur utilisateur2) {
        this.participants = new ArrayList<Utilisateur>();
        this.participants.add(utilisateur1);
        this.participants.add(utilisateur2);
        this.messages = new ArrayList<Message>();
    }

    /**
     * Méthode qui ajoute un message à la discussion
     * @param message
     * @throws ExceptionUtilisateurNonAutorise
     */
    public void ajouterMessage(Message message) throws ExceptionUtilisateurNonAutorise
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Le message ne peut pas être null");
        }
        Utilisateur Auteur = message.getUtilisateur();
        if (!utilisateurFaitParti(Auteur.getId()))
        {
            throw new ExceptionUtilisateurNonAutorise("Accès refusé",Auteur.getId(),this.getId());
        }

        this.messages.add(message);
        message.setDiscussion(this);
    }

    /**
     * Méthode qui vérifie si un utilisateur fais parti de la discussion
     * @param id
     * @return true si l'utilisateur fais parti, false sinon
     */
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

    public int getId()
    {
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
