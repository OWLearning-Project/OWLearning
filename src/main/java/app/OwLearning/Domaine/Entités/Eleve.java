package app.OwLearning.Domaine.Entités;

import lombok.Getter;
import lombok.Setter;

/**
 * classe Eleve qui représente un étudiant
 */
@Getter
@Setter
public class Eleve extends Utilisateur {

    private int age;

    private String niveauEtude;

    /**
     * Constructeur vide de Eleve
     */
    public Eleve() {
        super();
    }

    /**
     * Constructeur de Eleve
     * @param nom
     * @param prenom
     * @param email
     * @param motDePasseHash
     */
    public Eleve(String nom, String prenom, String email, String motDePasseHash)
    {
        super(nom, prenom, email, motDePasseHash);
    }

    public String toString()
    {
        return super.toString() + ", Niveau : " + this.getNiveauEtude() + ", Age : " + this.getAge();
    }
}

