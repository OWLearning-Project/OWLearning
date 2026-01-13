package Domain.Models;

import jakarta.persistence.*;

@Entity
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ressource")
    private int id_ressource;

    private String nom;
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_type_ressource")
    private TypeRessource type;

    public Ressource() {
    }

    public Ressource(String unNom, TypeRessource unType, String unUrl) {
        this.nom = unNom;
        this.type = unType;
        this.url = unUrl;
    }

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