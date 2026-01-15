package Domain.Models;

public enum Difficulte
{
    DEBUTANT("Débutant"),
    INTERMEDIAIRE("Intermédiaire"),
    AVANCE("Avancé");

    private final String label;

    private Difficulte(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
