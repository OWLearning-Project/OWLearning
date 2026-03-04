package app.OwLearning.Domain.Models;

import jakarta.persistence.*;

/**
 * classe Eleve qui représente un étudiant
 */
@Entity
@Table(name = "eleve")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class Eleve extends Utilisateur {

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
        if (age <= 0) {
            throw new IllegalArgumentException("Age invalide");
        }
        if (niveauEtude == null || niveauEtude.isBlank()) {
            throw new IllegalArgumentException("Niveau invalide");
        }
        this.age = age;
        this.niveauEtude = niveauEtude;
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

