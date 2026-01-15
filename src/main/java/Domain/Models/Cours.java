package Domain.Models;
import java.sql.Timestamp;
import java.util.*;

import Shared.Exceptions.ExceptionMauvaisIdCategorie;
import Shared.Exceptions.ExceptionMauvaisIdChapitre;
import Shared.Exceptions.ExceptionMauvaisIdEleve;
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
    @ManyToOne
    @JoinColumn(name="id_niveau")
    private Difficulte difficulte;
    @OneToMany
    @JoinColumn(name="id_cours")
    private ArrayList<Chapitre> chapitres;
    @ManyToMany
    @JoinTable(name="categorie_cours",
                joinColumns=@JoinColumn(name="id_cours"),
                inverseJoinColumns=@JoinColumn(name="id_categorie")
    )
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
            throw new IllegalArgumentException("un chapitre ne doit pas être null");
        this.chapitres.add(chapitre);
        chapitre.setCours(this);
    }

    public Chapitre retirerChapitre(int chapitreId) throws ExceptionMauvaisIdChapitre
    {
        Chapitre chapitre;
        if (chapitreId < 0)
            throw new ExceptionMauvaisIdChapitre("Id impossible", chapitreId, this.id);
        int i = 0;
        while(i<this.chapitres.size() && this.chapitres.get(i).getId() != chapitreId)
            i ++;
        if (i == this.chapitres.size())
            throw new ExceptionMauvaisIdChapitre("Chapitre inexistant", chapitreId, this.id);
        return this.chapitres.remove(i);
    }

    public void visibilite(boolean estPrive)
    {
        this.estPrive = estPrive;
    }

    public void ajouterCategorie(Categorie categorie)
    {
        if(categorie == null)
            throw new IllegalArgumentException("une categorie ne doit pas être null");
        this.categories.add(categorie);
    }

    public Categorie supprimerCategorie(int categorieId) throws ExceptionMauvaisIdCategorie {
        Categorie categorie;
        if (categorieId < 0)
            throw new ExceptionMauvaisIdCategorie("Id impossible", categorieId, this.id);
        int i = 0;
        while(i<this.categories.size() && this.categories.get(i).getIdNiveau() != categorieId)
            i ++;
        if (i == this.categories.size())
            throw new ExceptionMauvaisIdCategorie("Categorie inexistante", categorieId, this.id);
        return this.categories.remove(i);
    }

    public void ajouterEleve(Eleve eleve) throws IllegalArgumentException
    {
        if(eleve == null)
            throw new IllegalArgumentException("un eleve ne doit pas être null");
        this.eleves.add(eleve);
    }

    public Eleve supprimerEleve(int eleveId) throws ExceptionMauvaisIdEleve {
        Eleve eleve;
        if (eleveId < 0)
            throw new ExceptionMauvaisIdEleve("Id impossible", eleveId, this.id);
        int i = 0;
        while (i < this.eleves.size() && this.eleves.get(i).getId() != eleveId)
            i++;
        if (i == this.eleves.size())
            throw new ExceptionMauvaisIdEleve("Categorie inexistante", eleveId, this.id);
        return this.eleves.remove(i);
    }
}