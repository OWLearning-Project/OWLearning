package Domain.Models;
import java.sql.Timestamp;
import java.util.*;

import Shared.Exceptions.*;
import jakarta.persistence.*;

@Entity
public class Cours
{
    @Id
    @Column(name="id_cours")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    @Column(name="date_creation")
    private Timestamp dateCreation;
    @Column(name="est_prive")
    private boolean estPrive;
    @Column(name="est_publie")
    private boolean estPublie;
    @ManyToOne
    @JoinColumn(name="id_createur")
    private Createur createur;
    @ManyToMany
    @JoinTable( name="inscription",
                joinColumns=@JoinColumn(name="id_cours"),
                inverseJoinColumns=@JoinColumn(name="id_eleve")
    )
    private ArrayList<Eleve> eleves;
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulte")
    private Difficulte difficulte;
    @OneToMany
    @JoinColumn(name="id_cours")
    private ArrayList<Chapitre> chapitres;
    @ElementCollection(targetClass = Categorie.class)
    @CollectionTable(
            name = "categorie_cours",
            joinColumns = @JoinColumn(name = "id_cours")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private ArrayList<Categorie> categories;

    public Cours(){}

    public Cours(String titre, String description, boolean estPrive, ArrayList<Categorie> categories, Difficulte difficulte, Createur createur)
    {
        this.titre = titre;
        this.description = description;
        this.estPrive = estPrive;
        this.categories = categories;
        this.difficulte = difficulte;
        this.createur = createur;
        this.eleves = new ArrayList<Eleve>();
        this.chapitres = new ArrayList<Chapitre>();
        this.dateCreation = new Timestamp(System.currentTimeMillis());
    }

    public int getId()
    {
        return this.id;
    }

    public String getTitre()
    {
        return this.titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Timestamp getDateCreation()
    {
        return this.dateCreation;
    }

    public boolean getEstPrive()
    {
        return this.estPrive;
    }

    public void setEstPrive(boolean estPrive)
    {
        this.estPrive = estPrive;
    }

    public boolean getEstPublie()
    {
        return this.estPublie;
    }

    public Createur getCreateur()
    {
        return this.createur;
    }

    public ArrayList<Eleve> getEleves()
    {
        return this.eleves;
    }

    public Difficulte getDifficulte()
    {
        return this.difficulte;
    }

    public void setDifficulte(Difficulte difficulte)
    {
        this.difficulte = difficulte;
    }

    public ArrayList<Chapitre> getChapitres()
    {
        return this.chapitres;
    }

    public ArrayList<Categorie> getCategories()
    {
        return this.categories;
    }

    public void publier()
    {
        this.estPublie = true;
    }

    public void ajouterChapitre(Chapitre chapitre) throws IllegalArgumentException
    {
        if(chapitre == null)
            throw new IllegalArgumentException("Ajout de chapitre impossible");
        this.chapitres.add(chapitre);
        chapitre.setCours(this);
    }

    public Chapitre retirerChapitre(int chapitreId) throws ExceptionMauvaisIdChapitre
    {
        Chapitre chapitre;
        if (chapitreId < 0)
            throw new ExceptionMauvaisIdChapitre("Id impossible", chapitreId, this.getId());
        int i = 0;
        while(i<this.chapitres.size() && this.chapitres.get(i).getId() != chapitreId)
            i ++;
        if (i == this.chapitres.size())
            throw new ExceptionMauvaisIdChapitre("Chapitre inexistant", chapitreId, this.getId());
        this.chapitres.get(i).setCours(null);
        return this.chapitres.remove(i);
    }

    public void visibilite(boolean estPrive)
    {
        this.estPrive = estPrive;
    }

    public void ajouterCategorie(Categorie categorie) throws IllegalArgumentException, ExceptionCategorieDejaPresente
    {
        if(categorie == null)
            throw new IllegalArgumentException("Ajout de catégorie impossible");
        else
        {
            for(Categorie uneCategorie : this.getCategories())
            {
                if(uneCategorie.getLabel().equals(categorie.getLabel()))
                    throw new ExceptionCategorieDejaPresente("Impossible d'ajouter la catégorie", categorie.getLabel(), this.getId());
            }
        }
        this.categories.add(categorie);
    }

    public Categorie supprimerCategorie(String label) throws ExceptionMauvaisLabelCategorie
    {
        if (!Categorie.existeCategorie(label))
            throw new ExceptionMauvaisLabelCategorie("Label non existant", label, this.getId());
        int i = 0;
        while(i<this.categories.size() && !this.categories.get(i).getLabel().equals(label))
            i ++;
        if (i == this.categories.size())
            throw new ExceptionMauvaisLabelCategorie("Categorie non présente", label, this.getId());
        return this.categories.remove(i);
    }

    public void ajouterEleve(Eleve eleve) throws IllegalArgumentException
    {
        if(eleve == null)
            throw new IllegalArgumentException("Ajout d'élève impossible");
        else
        {
            for(Eleve unEleve : this.getEleves())
            {
                if(unEleve.getId() == eleve.getId())
                    throw new ExceptionEleveDejaPresent("Elève déjà inscrit", eleve.getId(), this.getId());
            }
        }
        this.eleves.add(eleve);
    }

    public Eleve supprimerEleve(int eleveId) throws ExceptionMauvaisIdEleve
    {
        Eleve eleve;
        if (eleveId < 0)
            throw new ExceptionMauvaisIdEleve("Id impossible", eleveId, this.getId());
        int i = 0;
        while (i < this.eleves.size() && this.eleves.get(i).getId() != eleveId)
            i++;
        if (i == this.eleves.size())
            throw new ExceptionMauvaisIdEleve("Eleve non inscrit", eleveId, this.getId());
        return this.eleves.remove(i);
    }

    private String toStringChapitres()
    {
        String s = "";
        if (this.chapitres != null)
        {
            for (int i = 0; i < this.chapitres.size(); i++)
            {
                s += " " + this.chapitres.get(i).toString() + "\n";
            }
        }
        return "Chapitres : \n[\n" + s + "]\n";
    }

    private String toStringCategories()
    {
        String s = "";
        if (this.categories != null)
        {
            for (int i = 0; i < this.categories.size(); i++)
            {
                s += " " + this.categories.get(i).toString() + "\n";
            }
        }
        return "Categories : \n[\n" + s + "]\n";
    }

    private String toStringEleves()
    {
        String s = "";
        if (this.eleves != null)
        {
            for (int i = 0; i < this.eleves.size(); i++)
            {
                s += " " + this.eleves.get(i).toString() + "\n";
            }
        }
        return "Eleves Inscrits : \n[\n" + s + "]\n";
    }

    public String toString()
    {
        String s = "Cours #" + this.getId() + " : " + this.getTitre() + "\n" +
                "Description : " + this.getDescription() + "\n" +
                "Difficulté : " + this.getDifficulte() + "\n" +
                "Créateur : " + (this.createur != null ? this.createur.getPrenom() + " " + this.createur.getNom() : "Inconnu") + "\n";

        return s + "\n" +
                this.toStringCategories() +
                this.toStringChapitres() +
                this.toStringEleves();
    }
}