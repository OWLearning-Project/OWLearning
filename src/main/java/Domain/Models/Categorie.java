package Domain.Models;

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

    private final String label;

    /**
     * Constructeur de catégorie
     * @param label
     */
    private Categorie(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
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
            if (uneCategorie.getLabel().equals(label))
                return true;
        }
        return false;
    }

    public String toString()
    {
        return this.getLabel();
    }
}