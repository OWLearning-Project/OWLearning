package app.OwLearning.Domain.Models;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe d'Utilisateur qui permet de créer un utilisateur
 */
@Getter
@Setter
public class Utilisateur 
{
    private int idUtilisateur; 
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String motDePasseHash;
    private Timestamp dateInscription;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Utilisateur))
            return false;
        Utilisateur that = (Utilisateur) o;
        if (this.email != null ? !this.email.equals(that.email) : that.email != null)
            return false;
        return true;
    }

    public String toString()
    {
        return "Nom : " + this.nom + ", Prenom : " + this.prenom + ", Email : " + this.email + ", Inscrit le : " + this.dateInscription ;
    }
}
