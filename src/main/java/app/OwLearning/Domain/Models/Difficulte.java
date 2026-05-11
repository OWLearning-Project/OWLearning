package app.OwLearning.Domain.Models;

import lombok.Getter;

/**
 * Enum Difficulté qui répértorie les différent niveaux de difficultés d'un cours
 */
public enum Difficulte
{
    DEBUTANT("Débutant"),
    INTERMEDIAIRE("Intermédiaire"),
    AVANCE("Avancé");

    @Getter
    private final String label;

    /**
     * Constructeur de Difficulté
     * @param label
     */
    private Difficulte(String label)
    {
        this.label = label;
    }

}
