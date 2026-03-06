package app.OwLearning.Domain.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Chaptire qui permet de construire un chapitre en lui ajoutant/retirant des ressources et de l'attribuer à un cours
 */
@Entity
public class Chapitre
{
    @Id
    @Column(name="id_chapitre")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String titre;
    @ManyToOne
    @JoinColumn(name = "id_cours")
    private Cours cours;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_chapitre")
    private List<Ressource> ressources;
    public Chapitre(){}

    /**
     * Constructeur de Chaptire
     * @param titre
     * @param description
     * @param ressources
     */
    public Chapitre(String titre, String description, ArrayList<Ressource> ressources)
    {
        this.titre = titre;
        this.description = description;
        this.ressources = ressources;
    }

    public int getId()
    {
        return this.id;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Cours getCours() {
        return this.cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public List<Ressource> getRessources() {
        return ressources;
    }

    public void setRessources(ArrayList<Ressource> ressources) {
        this.ressources = ressources;
    }

    /**
     * Méthode qui ajoute une ressource au chapitre
     * @param ressource
     */
    public void ajouterRessource(Ressource ressource)
    {
        this.ressources.add(ressource);
    }

    /**
     * Méthode qui supprime une ressource du chapitre
     * @param ressource
     * @return la ressource supprimé
     */
    public Ressource supprimerRessource(Ressource ressource){
        if (this.ressources.remove(ressource))
        {
            return ressource;
        }
        return null;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Chapitre chapitre = (Chapitre) o;
        if (this.id == 0 || chapitre.id == 0)
            return false;
        return id == chapitre.id;
    }
    private String toStringRessources()
    {
        String s = "";
        if (this.ressources != null)
        {
            for (int i = 0; i < this.ressources.size(); i++)
            {
                s += "   - " + this.ressources.get(i).toString() + "\n";
            }
        }
        return "Ressources : \n   [\n" + s + "   ]";
    }

    public String toString()
    {
        return "Chapitre " + this.getId() + " : " + this.getTitre() + " (" + this.getDescription() + ")\n" +
                this.toStringRessources();
    }
}
