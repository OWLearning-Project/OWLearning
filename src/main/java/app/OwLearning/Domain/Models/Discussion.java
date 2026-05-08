package app.OwLearning.Domain.Models;
import java.util.ArrayList;
import java.util.List;

import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe Discussion qui permet de créer une discussion en gérant ses participants et les messages de la discussion
 */
@Getter
@Setter
public class Discussion 
{
    private int idDiscussion;
    private List<Utilisateur> participants;
    private List<Message> messages;

    /**
     * Constructeur par défaut
     */
    public Discussion(){}

    /**
     * Constructeur de Discussion
     * @param utilisateur1
     * @param utilisateur2
     */
    public Discussion(Utilisateur utilisateur1, Utilisateur utilisateur2)
    {
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
        if (!utilisateurFaitParti(Auteur.getIdUtilisateur()))
        {
            throw new ExceptionUtilisateurNonAutorise("Accès refusé",Auteur.getIdUtilisateur(),this.getIdDiscussion());
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
            if(this.participants.get(i).getIdUtilisateur() == id)
            {
                faitParti = true;
            }
        }
        return faitParti;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Discussion that = (Discussion) o;
        if (this.idDiscussion == 0 || that.idDiscussion == 0)
            return false;
        return idDiscussion == that.idDiscussion;
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
