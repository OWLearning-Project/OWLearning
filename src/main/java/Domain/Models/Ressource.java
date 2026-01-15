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

    @ManyToOne
    @JoinColumn(name = "id_typeRessource")
    private TypeRessource type;

    public Ressource() {
    }

    public Ressource(String unNom, TypeRessource unType, String unUrl) {
        this.nom = unNom;
        this.type = unType;
        this.url = unUrl;
    }

    public int getId_ressource() {
        return this.id_ressource;
    }

    public String getNom() {
        return this.nom;
    }

    public String getUrl() {
        return this.url;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}