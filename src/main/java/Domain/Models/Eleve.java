package Domain.Models;

import jakarta.persistence.*;

/**
 * classe Eleve qui représente un étudiant
 */
@Entity
@Table(name = "eleve")
public class Eleve extends Utilisateur {

    @Column(nullable = false)
    private int age;

    @Column(name = "niveau_etude")
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

    public int getAge() {
        return age;
    }

    public void setAge(int unAge) {
        this.age = unAge;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String unNiveauEtude) {
        this.niveauEtude = unNiveauEtude;
    }

    public String toString()
    {
        return super.toString() + ", Niveau : " + this.getNiveauEtude() + ", Age : " + this.getAge();
    }
}

