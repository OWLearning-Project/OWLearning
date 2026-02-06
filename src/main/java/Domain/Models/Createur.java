package Domain.Models;

import jakarta.persistence.*;


@Entity
@Table(name = "createur")
public class Createur extends Utilisateur
{
    /**
     * Constructeur vide Createur
     */
    public Createur()
    {
        super();
    }

    /**
     * Constructeur Createur
     * @param nom
     * @param prenom
     * @param email
     * @param motDePasseHash
     */
    public Createur(String nom, String prenom, String email, String motDePasseHash)
    {
        super(nom, prenom, email, motDePasseHash);
    }
}


