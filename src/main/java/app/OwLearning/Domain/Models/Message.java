package app.OwLearning.Domain.Models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe Message qui permet de créer un message avec son contenu l'assigner à sa discussion et à l'Utilisateur qui l'a envoyé
 */
@Getter
@Setter
public class Message {
    private int id_message;

    private Timestamp dateCreation;
    private String contenu;

    private StatutMessage statutMessage;

    private Discussion discussion;

    private Utilisateur utilisateur;

    private List<Ressource> ressources = new ArrayList<>();

    /**
     * Constructeur vide de Message
     */
    public Message()
    {
    }

    /**
     * Constructeur de Message
     * @param unContenu
     * @param unAuteur
     */
    public Message(String unContenu, Utilisateur unAuteur)
    {
        this.utilisateur = unAuteur;
        this.discussion = null;
        this.contenu = unContenu;
    }

    /**
     * Méthode qui permet d'ajoute une ressource au message (PJ)
     * @param uneRessource
     * @throws IllegalArgumentException
     */
    public void ajouterRessource(Ressource uneRessource) throws IllegalArgumentException
    {
        if (uneRessource == null)
        {
            throw new IllegalArgumentException("Une ressource null ne peut être ajouter");
        }
        this.ressources.add(uneRessource);
    }

    /**
     * Méthode qui permet de retirer une ressource au message
     * @param uneRessourceId
     * @return la ressource retiré
     */
    public Ressource retirerRessource(int uneRessourceId)
    {

        for(int i = 0; i < ressources.size(); i++)
        {
            Ressource ressourceARetirer = ressources.get(i);

            if(ressourceARetirer.getId_ressource() == uneRessourceId)
            {
                this.ressources.remove(i);
                return ressourceARetirer;
            }
        }
        throw new NoSuchElementException("Aucune ressource trouvée avec l'ID " + uneRessourceId);
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Message message = (Message) o;
        if (this.id_message == 0 || message.id_message == 0)
            return false;
        return id_message == message.id_message;
    }

    public String toString()
    {
        return "Auteur : " + "(" + this.getUtilisateur() + "), Contenu : " + this.getContenu() ;
    }
}
