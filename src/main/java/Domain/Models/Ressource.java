package Domain.Models;

public class Ressource {
    private int id_ressource;
    private String nom;
    private String url;

    public int getId_ressource() {
        return id_ressource;
    }
    public String getNom() {
        return nom;
    }
    public String getUrl() {
        return url;
    }

    public void setId_ressource(int id_ressource) {
        this.id_ressource = id_ressource;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}