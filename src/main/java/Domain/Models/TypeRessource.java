package Domain.Models;

public enum TypeRessource
{
    VIDEO("Vidéo"),
    FICHIER_PDF("Fichier PDF"),
    FICHIER_ZIP("Fichier Zip"),
    IMAGE("Image");

    private final String label;

    private TypeRessource (String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return this.label;
    }

}
