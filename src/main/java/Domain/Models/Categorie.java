package Domain.Models;

import jakarta.persistence.*;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categorie;
    private String nom;

    public Categorie(){}

    public Categorie(String nom)
    {
        this.nom = nom;
    }

    public int getIdNiveau()
    {
        return this.id_categorie;
    }

    public String getNiveau()
    {
        return this.nom;
    }
}