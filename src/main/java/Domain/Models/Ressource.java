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
    @Column(name = "type_ressource")
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

    public String toString()
    {
        String labelType = "";
        if (this.type == null)
        {
            labelType = "Type inconnu";
        }
        labelType += this.type.getLabel();
        return "[" + labelType + "] " + this.getNom() + " (" + this.getUrl() + ")";
    }
}