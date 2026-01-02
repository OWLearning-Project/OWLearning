package Domain.Models;
import java.sql.Timestamp;
import jakarta.persistence.*;

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
    private String email;
    @Column(name="date_inscription")
    private Timestamp dateInscription;
    @Column(name="derniere_activite")
    private Timestamp derniereActivite;   

    public Utilisateur()
    {}

    public Utilisateur(String nom, String prenom, String email)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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

    public Timestamp getDateInscription() {
        return this.dateInscription;
    }

    public void setDateInscription(Timestamp uneDateInscription) {
        this.dateInscription = uneDateInscription;
    }

    public Timestamp getDerniereActivite() {
        return this.derniereActivite;
    }

    public void setDerniereActivite(Timestamp uneDerniereActivite) {
        this.derniereActivite = uneDerniereActivite;
    }
}
