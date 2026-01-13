package Domain.Models;
import java.sql.Timestamp;
import java.util.*;
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
    private Createur createur;
    private ArrayList<Eleve> eleves;
    private Difficulte difficulte;
    private ArrayList<Chapitre> chapitres;
    private ArrayList<Categorie> categories;

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

    public void publier()
    {

    }

    public void ajouterChapitre(Chapitre chapitre)
    {

    }

    public Chapitre retirerChapitre(int chapitreId)
    {
        return null;
    }

    public void visibilite(boolean estPrive)
    {

    }

    public void ajouterCategorie(Categorie categorie)
    {

    }

    public Categorie supprimerCategorie(int categorieId)
    {
        return null;
    }

    public void ajouterEleve(Eleve eleve)
    {

    }

    public Eleve supprimerEleve(int eleveId)
    {
        return null;
    }
    
}