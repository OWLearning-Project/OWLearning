package app.OwLearning.Domain.Models;

import lombok.Getter;

/**
 * Enum TypeRessource qui répértorie les différents types de ressources possbile pour une PJ
 */
public enum TypeRessource
{
    VIDEO("Vidéo"),
    FICHIER_PDF("Fichier PDF"),
    FICHIER_ZIP("Fichier Zip"),
    IMAGE("Image");

    @Getter
    private final String label;

    /**
     * Constructeur de TypeRessource
     * @param label
     */
    private TypeRessource (String label)
    {
        this.label = label;
    }

}
