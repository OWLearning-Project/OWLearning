package Domain.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "eleve")
public class Eleve extends Utilisateur {

    @Column(nullable = false)
    private int age;

    @Column(name = "niveau_etude")
    private String niveauEtude;

    public Eleve() {
        super();
    }

    public Eleve(String nom, String prenom, String email, String motDePasseHash,
                 int age, String niveauEtude) {
        super(nom, prenom, email, motDePasseHash);
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
    
}

