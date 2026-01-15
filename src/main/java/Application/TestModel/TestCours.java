package Application.TestModel;

import Domain.Models.*;
import Shared.Exceptions.ExceptionMauvaisIdCategorie;
import Shared.Exceptions.ExceptionMauvaisIdChapitre;
import Shared.Exceptions.ExceptionMauvaisIdEleve;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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
    public void testRetirerChapitre() throws ExceptionMauvaisIdChapitre {
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
        Chapitre chapitreTest1 = mock(Chapitre.class);
        Chapitre chapitreTest2 = mock(Chapitre.class);

        // Act & Assert
        when(chapitreTest1.getId()).thenReturn(0);
        when(chapitreTest2.getId()).thenReturn(5);

        assertThrows(ExceptionMauvaisIdChapitre.class, ()->cours.retirerChapitre(3));
        assertThrows(ExceptionMauvaisIdChapitre.class, ()->cours.retirerChapitre(-1));
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

    @Test
    public void testLeCoursDevientPrive()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.visibilite(true);

        // Assert
        assertTrue(cours.getEstPrive());
    }

    @Test
    public void testLeCoursDevientPublic()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", true, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.visibilite(false);

        // Assert
        assertFalse(cours.getEstPrive());
    }

    @Test
    public void testLeCoursRestePublic()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.visibilite(false);

        // Assert
        assertFalse(cours.getEstPrive());
    }
    @Test
    public void testLeCoursRestePrive()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", true, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act
        cours.visibilite(true);

        // Assert
        assertTrue(cours.getEstPrive());
    }

    @Test
    public void testRetirerCategorie() throws ExceptionMauvaisIdCategorie
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = new Categorie("Base de données");
        categories.add(categorieTest);
        Cours cours = new Cours("unTitre", "uneDescription", false, categories, new Difficulte(), new Createur());


        // Act
        Categorie categorieRetire = cours.supprimerCategorie(0);

        // Assert
        assertEquals(categorieTest, categorieRetire);
        assertFalse(cours.getChapitres().contains(categorieTest));
    }

    @Test
    public void testRetirerCategorieNonExistante()
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = mock(Categorie.class);
        categories.add(categorieTest);

        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());

        // Act & Assert
        when(categorieTest.getIdNiveau()).thenReturn(4);

        assertThrows(ExceptionMauvaisIdCategorie.class, ()->cours.supprimerCategorie(0));
        assertThrows(ExceptionMauvaisIdCategorie.class, ()->cours.supprimerCategorie(-1));
    }

    @Test
    public void testRetirerEleve() throws ExceptionMauvaisIdChapitre, ExceptionMauvaisIdEleve {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());
        Eleve eleveTest = new Eleve("Bob", "Martin", "bobmartin@email.com", "fauxmotdepasse", 23, "BUT3");

        cours.ajouterEleve(eleveTest);

        // Act
        Eleve eleveRetire = cours.supprimerEleve(0);

        // Assert
        assertEquals(eleveTest, eleveRetire);
        assertFalse(cours.getChapitres().contains(eleveTest));
    }

    @Test
    public void testRetirerEleveNonExistant()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), new Difficulte(), new Createur());
        Eleve eleveTest = mock(Eleve.class);

        // Act & Assert
        when(eleveTest.getId()).thenReturn(6);

        assertThrows(ExceptionMauvaisIdEleve.class, ()->cours.supprimerEleve(3));
        assertThrows(ExceptionMauvaisIdEleve.class, ()->cours.supprimerEleve(-1));
    }
}
