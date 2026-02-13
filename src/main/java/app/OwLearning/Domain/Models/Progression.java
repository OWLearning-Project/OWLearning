package app.OwLearning.Domain.Models;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Classe de Progression qui permet de créer une Progression en calculant le taux de progression d'un cours.
 */
@Entity
@Table(name = "progression")
public class Progression
{
    @EmbeddedId
    private ProgressionId id;

    @ManyToOne
    @MapsId("idCours")
    @JoinColumn(name="id_cours")
    private Cours cours;

    @ManyToOne
    @MapsId("idEleve")
    @JoinColumn(name="id_eleve")
    private Eleve eleve;

    @Column(name="taux_progression", nullable = false)
    private float tauxProgression = 0;

    /**
     * Constructeur vide de Progression
     */
    public Progression()
    {
    }

    /**
     * Constructeur de Progression
     * @param cours
     * @param eleve
     */
    public Progression(Cours cours, Eleve eleve)
    {
        this.id = new ProgressionId(cours.getId(), eleve.getId());
        this.cours = cours;
        this.eleve = eleve;
    }

    public float getTauxProgression()
    {
        return this.tauxProgression;
    }

    public Cours getCours()
    {
        return cours;
    }
    public Eleve getEleve()
    {
        return eleve;
    }

    public void setTauxProgression(float unTauxProgression)
    {
        this.tauxProgression = unTauxProgression;
    }

    public boolean estCoursTermine() {
        return this.tauxProgression >= 100.0;
    }

    /**
     * Méthode qui permet de calculer le pourcentage de progression d'un cours
     * @param nbChapitresTermines
     * @param nbTotalChapitres
     */
    public void calculerPourcentage(int nbChapitresTermines, int nbTotalChapitres) {
        if (nbTotalChapitres == 0) {
            this.tauxProgression = 0;
        } else {
            // Correspond au calcul SQL : (nb_fini::NUMERIC / nb_total::NUMERIC) * 100
            float resultat = ((float) nbChapitresTermines / nbTotalChapitres) * 100;
            this.tauxProgression = Math.round(resultat * 100.0f) / 100.0f;
        }
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Progression that = (Progression) o;
        return Objects.equals(cours, that.cours) &&
                Objects.equals(eleve, that.eleve);
    }

    public String toString()
    {
        return "Progression de " + this.getEleve().getPrenom() + " sur " + this.getCours().getTitre() + " : " + this.getTauxProgression() + "%";
    }
}
