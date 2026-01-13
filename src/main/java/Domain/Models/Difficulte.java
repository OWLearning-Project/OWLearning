package Domain.Models;

import jakarta.persistence.*;

@Entity
public class Difficulte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_niveau;
    private String niveau;

    public Difficulte(){}

    public Difficulte(String niveau)
    {
        this.niveau = niveau;
    }

    public int getIdNiveau()
    {
        return this.id_niveau;
    }

    public String getNiveau()
    {
        return this.niveau;
    }
}
