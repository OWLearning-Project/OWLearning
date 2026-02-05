package Domain.Models;

import jakarta.persistence.*;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    private Timestamp date_creation;
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutMessage statutMessage;

    @ManyToOne
    @JoinColumn(name = "id_discussion")
    private Discussion discussion;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @OneToMany
    @JoinColumn(name = "id_message")
    private ArrayList<Ressource> ressources = new ArrayList<>();

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

    public Timestamp getDate_creation()
    {
        return this.date_creation;
    }

    public String getContenu()
    {
        return this.contenu;
    }

    public ArrayList<Ressource> getRessources()
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

    public void setDate_creation(Timestamp date_creation)
    {
        this.date_creation = date_creation;
    }

    public void setContenu(String contenu)
    {
        this.contenu = contenu;
    }

    public void setRessources(ArrayList<Ressource> desRessources)
    {
        this.ressources = desRessources;
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

    public String toString()
    {
        return "Auteur : " + "(" + this.getUtilisateur() + "), Contenu : " + this.getContenu() ;
    }
}
