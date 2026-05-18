package app.OwLearning.Domaine.Entités;

import app.OwLearning.Domaine.Enumérations.TypeRessource;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe Ressource qui représente les pièces jointes
 */
@Getter
@Setter
public class Ressource {

    private int id_ressource;

    private String nom;
    private String url;

    private TypeRessource type;

    /**
     * Constructeur vide de Ressource
     */
    public Ressource() {
    }

    /**
     * Constructeur de Ressource
     * @param unNom
     * @param unType
     * @param unUrl
     */
    public Ressource(String unNom, TypeRessource unType, String unUrl)
    {
        this.nom = unNom;
        this.type = unType;
        this.url = unUrl;
    }

    public String toString()
    {
        String labelType = "";
        if (this.type == null)
        {
            labelType = "Type inconnu";
        }
        labelType += this.type.getLabel();
        return "[" + labelType + "] " + this.getNom() + " (" + this.getUrl() + ")";
    }
}
