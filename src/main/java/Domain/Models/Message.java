package Domain.Models;

import jakarta.persistence.*;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private int id_message;

    private Timestamp date_creation;
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "id_statutMessage")
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

    public Message() {
    }

    public Message(String unContenu, Utilisateur unAuteur, Discussion uneDiscussion) {
        this.utilisateur = unAuteur;
        this.discussion = uneDiscussion;
        this.contenu = unContenu;
    }


    public int getId_message() {
        return this.id_message;
    }

    public Timestamp getDate_creation() {
        return this.date_creation;
    }

    public String getContenu() {
        return this.contenu;
    }

    public ArrayList<Ressource> getRessources(){
        return this.ressources;
    }

    public Discussion getDiscussion(){
        return this.discussion;
    }

    public Utilisateur getAuteur(){
        return this.utilisateur;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setRessources(ArrayList<Ressource> desRessources){
        this.ressources = desRessources;
    }

    public void setDiscussion(Discussion uneDiscussion) {
        this.discussion = uneDiscussion;
    }

    public void setUtilisateur(Utilisateur unUtilisateur) {
        this.utilisateur = unUtilisateur;
    }

    public void ajouterRessource(Ressource uneRessource){
        this.ressources.add(uneRessource);
    }

    public Ressource retirerRessource(int uneRessourceId){

        for(int i = 0; i < ressources.size(); i++){
            Ressource ressourceARetirer = ressources.get(i);

            if(ressourceARetirer.getId_ressource() == uneRessourceId){
                this.ressources.remove(i);
                return ressourceARetirer;
            }
        }
        return null;
    }
}
