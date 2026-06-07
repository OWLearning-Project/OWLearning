package app.OwLearning.Domaine.Enumérations;

import app.OwLearning.Domaine.Exceptions.ExceptionCategorieInexistante;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * Enum Categorie qui répértorie les différents types de cours
 */
public enum Categorie
{

    PROGRAMMATION_ALGORITHMIQUE("Programmation algorithmique"),
    DEVELOPPEMENT_WEB("Développement web"),
    BASE_DE_DONNEES("Base de données"),
    SYSTEMES_RESEAUX("Systèmes et réseaux"),
    IA_DATASCIENCES("Intelligence artificielle et DataSciences"),
    DEVELOPPEMENT_MOBILE("Développement mobile"),
    HISTOIRE_INFORMATIQUE("Histoire de l'informatique"),
    MANAGEMENT_GESTION("Management et gestion"),
    MATHEMATIQUES("Mathématiques"),
    ARCHITECTURE("Architecture");

    @Getter
    private final String label;

    /**
     * Constructeur de catégorie
     * @param label
     */
    private Categorie(String label)
    {
        this.label = label;
    }

    /**
     * Méthode qui vérifie si une catégorie existe bien
     * @param label
     * @return true si la catégorie existe false sinon
     */
    public static boolean existeCategorie(String label)
    {
        for (Categorie uneCategorie : Categorie.values())
        {
            if (uneCategorie.getLabel().equalsIgnoreCase(label))
                return true;
        }
        return false;
    }

    public static Categorie stringEnCategorie(String label)
    {
        for (Categorie uneCategorie : Categorie.values())
        {
            if (uneCategorie.getLabel().equalsIgnoreCase(label) || uneCategorie.name().equalsIgnoreCase(label))
                return uneCategorie;
        }
        throw new ExceptionCategorieInexistante("La catégorie n'existe pas", label);
    }

    @JsonCreator
    public static Categorie depuisJson(String valeur)
    {
        return stringEnCategorie(valeur);
    }

    public String toString()
    {
        return this.getLabel();
    }
}