package Application.TestModel;

import Domain.Models.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class TestCours
{
    @Test
    public void testPublierUnCours()
    {
        // Arrange
        Cours cours = new Cours();

        // Act
        cours.publier();

        // Assert
        assertTrue(cours.getEstPublie());
    }

    @Test
    public void testLeChapitreEstAjoute()
    {
        // Arrange
        Chapitre chapitreTest = new Chapitre("uneDescription", "unTitre", new ArrayList<Ressource>());
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.ajouterChapitre(chapitreTest);

        // Assert
        assertTrue(cours.getChapitres().contains(chapitreTest));
        assertEquals(chapitreTest.getCours(), cours);
    }

    @Test
    public void testLeChapitreEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterChapitre(null));
    }

    @Test
    public void testEleveEstAjoute()
    {
        // Arrange
        Eleve eleveTest = new Eleve("Bob", "Martin", "bobmartin@email.com", "fauxmotdepasse", 23, "BUT3");
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.ajouterEleve(eleveTest);

        // Assert
        assertTrue(cours.getEleves().contains(eleveTest));
    }

    @Test
    public void testEleveEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterEleve(null));
    }

    @Test
    public void testRetirerChapitre()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());
        Chapitre chapitreTest = new Chapitre("uneDescription", "unTitre", new ArrayList<Ressource>());

        cours.ajouterChapitre(chapitreTest);

        // Act
        Chapitre chapitreRetire = cours.retirerChapitre(0);

        // Assert
        assertEquals(chapitreRetire, chapitreTest);
        assertFalse(cours.getChapitres().contains(chapitreTest));
    }

    @Test
    public void testRetirerChapitreNonExistant()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());
        Chapitre chapitreTest = new Chapitre("uneDescription", "unTitre", new ArrayList<Ressource>());

        cours.ajouterChapitre(chapitreTest);

        // Act & Assert
        // A FINIR
    }

    @Test
    public void testLaCategorieEstAjoute()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());
        Categorie categorieTest = new Categorie("test");

        // Act
        cours.ajouterCategorie(categorieTest);

        // Assert
        assertTrue(cours.getCategories().contains(categorieTest));
    }

    @Test
    public void testLaCategorieEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterCategorie(null));
    }
}
