package Domain.Models;
import java.sql.Timestamp;
import jakarta.persistence.*;

/**
 * Classe d'Utilisateur qui permet de créer un utilisateur
 */
@Entity
public class Utilisateur 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_utilisateur")
    private int idUtilisateur; 
    private String nom;
    private String prenom;
    private String pseudo;
    @Column(unique = true)
    private String email;
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasseHash;
    @Column(name="date_inscription")
    private Timestamp dateInscription;
    @Column(name="derniere_activite")
    private Timestamp derniereActivite;

    /**
     * Constructeur vide de Utilisateur
     */
    public Utilisateur()
    {}

    /**
     * Constructeur de Utilisateur
     * @param nom
     * @param prenom
     * @param email
     * @param motDePasseHash
     */
    public Utilisateur(String nom, String prenom, String email, String motDePasseHash)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.dateInscription = new Timestamp(System.currentTimeMillis());
    }

    public int getId()
    {
        return this.idUtilisateur;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void setNom(String unNom) 
    {
        this.nom = unNom;
    }

    public String getPrenom()
    {
        return this.prenom;
    }
    public void setPrenom(String unPrenom)
    {
        this.prenom = unPrenom;
    }

    public String getPseudo()
    {
        return this.pseudo;
    }
    public void setPseudo(String unPseudo)
    {
        this.pseudo = unPseudo;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String unEmail)
    {
        this.email = unEmail;
    }
    public String getMotDePasse()
    {
        return this.motDePasseHash;
    }
    public void setMotDePasse(String unMotDePasse)
    {
        this.motDePasseHash = unMotDePasse;
    }

    public Timestamp getDateInscription()
    {
        return this.dateInscription;
    }

    public void setDateInscription(Timestamp uneDateInscription)
    {
        this.dateInscription = uneDateInscription;
    }

    public Timestamp getDerniereActivite()
    {
        return this.derniereActivite;
    }

    public void setDerniereActivite(Timestamp uneDerniereActivite)
    {
        this.derniereActivite = uneDerniereActivite;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Utilisateur))
            return false;
        Utilisateur that = (Utilisateur) o;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null)
            return false;
        return true;
    }
    public String toString()
    {
        return "Nom : " + this.getNom() + ", Prenom : " + this.getPrenom() + ", Email : " + this.getEmail() + ", Inscrit le : " + this.dateInscription ;
    }
}
