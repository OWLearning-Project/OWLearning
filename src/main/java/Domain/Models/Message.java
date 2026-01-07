package Domain.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private int id_message;

    private Timestamp date_creation;
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_statut")
    private StatutMessage statutMessage;

    @ManyToOne
    @JoinColumn(name = "id_discussion")
    private Discussion discussion;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    public Message() {
    }

    public Message(Timestamp uneDateCreation, String unContenu) {
        this.date_creation = uneDateCreation;
        this.contenu = unContenu;
    }


    public int getId_message() {
        return id_message;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

}
