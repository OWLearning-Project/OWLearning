package app.OwLearning.Domain.Models;

/**
 * Enum Difficulté qui répértorie les différent niveaux de difficultés d'un cours
 */
public enum Difficulte
{
    DEBUTANT("Débutant"),
    INTERMEDIAIRE("Intermédiaire"),
    AVANCE("Avancé");

    private final String label;

    /**
     * Constructeur de Difficulté
     * @param label
     */
    private Difficulte(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
