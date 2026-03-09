package app.OwLearning.Domain.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe Message qui permet de créer un message avec son contenu l'assigner à sa discussion et à l'Utilisateur qui l'a envoyé
 */
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private int id_message;

    @Column(name = "date_creation")
    private Timestamp dateCreation;
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutMessage statutMessage;

    @ManyToOne
    @JoinColumn(name = "id_discussion")
    @JsonIgnore
    private Discussion discussion;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToMany( fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE}) // Chargement des PJ avec le message & permet de sauver la liaison sans requete manuelle
    @JoinTable(name = "piece_jointe",
            joinColumns = @JoinColumn(name = "id_message"),
            inverseJoinColumns = @JoinColumn(name = "id_ressource"))
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

    public int getId_message()
    {
        return this.id_message;
    }

    public Timestamp getDateCreation()
    {
        return this.dateCreation;
    }

    public String getContenu()
    {
        return this.contenu;
    }

    public List<Ressource> getRessources()
    {
        return this.ressources;
    }

    public Discussion getDiscussion()
    {
        return this.discussion;
    }

    public Utilisateur getUtilisateur()
    {
        return this.utilisateur;
    }

    public StatutMessage getStatutMessage()
    {
        return this.statutMessage;
    }

    public void setDateCreation(Timestamp dateCreation)
    {
        this.dateCreation = dateCreation;
    }

    public void setContenu(String contenu)
    {
        this.contenu = contenu;
    }

    public void setRessources(List<Ressource> desRessources)
    {
        this.ressources = desRessources;
    }

    public void setStatutMessage(StatutMessage statutMessage)
    {
        this.statutMessage = statutMessage;
    }

    public void setDiscussion(Discussion uneDiscussion)
    {
        this.discussion = uneDiscussion;
    }

    public void setUtilisateur(Utilisateur unUtilisateur)
    {
        this.utilisateur = unUtilisateur;
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
