package Domain.Models;

import java.sql.Timestamp;

public class Message {
    private int id_message;
    private Timestamp date_creation;
    private String contenu;

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
