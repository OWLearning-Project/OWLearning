package Domain.Models;

public class Eleve extends Utilisateur {
    private int age;
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

    public void setAge(int age) {
        this.age = age;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "id=" + getId() +
                ", age=" + age +
                ", niveauEtude='" + niveauEtude + '\'' +
                '}';
    }
}


