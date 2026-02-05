package Domain.TestModel;

import Domain.Models.*;
import Shared.Exceptions.*;
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
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.ajouterChapitre(chapitreTest);

        // Assert
        assertTrue(cours.getChapitres().contains(chapitreTest));
        assertEquals(chapitreTest.getCours(), cours);
        assertEquals(chapitreTest.getCours().getId(), cours.getId());
    }

    @Test
    public void testLeChapitreEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterChapitre(null));
    }

    @Test
    public void testEleveEstAjoute()
    {
        // Arrange
        Eleve eleveTest = new Eleve("Bob", "Martin", "bobmartin@email.com", "fauxmotdepasse");
        eleveTest.setAge(23);
        eleveTest.setNiveauEtude("BUT3");
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.ajouterEleve(eleveTest);

        // Assert
        assertTrue(cours.getEleves().contains(eleveTest));
    }

    @Test
    public void testEleveEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterEleve(null));
    }

    @Test
    public void testRetirerChapitre() throws ExceptionMauvaisIdChapitre {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Chapitre chapitreTest = new Chapitre("uneDescription", "unTitre", new ArrayList<Ressource>());

        cours.ajouterChapitre(chapitreTest);

        // Act
        Chapitre chapitreRetire = cours.retirerChapitre(0);

        // Assert
        assertEquals(chapitreRetire, chapitreTest);
        assertFalse(cours.getChapitres().contains(chapitreTest));
        assertNull(chapitreRetire.getCours());
    }

    @Test
    public void testRetirerChapitreNonExistant()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
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
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Categorie categorieTest = Categorie.BASE_DE_DONNEES;

        // Act
        cours.ajouterCategorie(categorieTest);

        // Assert
        assertTrue(cours.getCategories().contains(categorieTest));
    }

    @Test
    public void testLaCategorieEstNull()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->cours.ajouterCategorie(null));
    }

    @Test
    public void testLeCoursDevientPrive()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.visibilite(true);

        // Assert
        assertTrue(cours.getEstPrive());
    }

    @Test
    public void testLeCoursDevientPublic()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", true, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.visibilite(false);

        // Assert
        assertFalse(cours.getEstPrive());
    }

    @Test
    public void testLeCoursRestePublic()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.visibilite(false);

        // Assert
        assertFalse(cours.getEstPrive());
    }
    @Test
    public void testLeCoursRestePrive()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", true, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act
        cours.visibilite(true);

        // Assert
        assertTrue(cours.getEstPrive());
    }

    @Test
    public void testRetirerCategorie() throws ExceptionMauvaisLabelCategorie
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = Categorie.BASE_DE_DONNEES;
        categories.add(categorieTest);
        Cours cours = new Cours("unTitre", "uneDescription", false, categories, Difficulte.DEBUTANT, new Createur());

        // Act
        Categorie categorieRetire = cours.supprimerCategorie("Base de données");

        // Assert
        assertEquals(categorieTest, categorieRetire);
        assertFalse(cours.getCategories().contains(categorieTest));
        assertTrue(cours.getCategories().isEmpty());
    }

    @Test
    public void testRetirerCategorieNonPresenteDansLaListe()
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = Categorie.IA_DATASCIENCES;
        categories.add(categorieTest);

        Cours cours = new Cours("unTitre", "uneDescription", false, categories, Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        Exception exception = assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie("Base de données"));
        assertEquals("Categorie non présente", exception.getMessage());
    }

    @Test
    public void testRetirerCategorieNonExistante()
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = Categorie.IA_DATASCIENCES;
        categories.add(categorieTest);

        Cours cours = new Cours("unTitre", "uneDescription", false, categories, Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        Exception exception = assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie("Economie"));
        assertEquals("Label non existant", exception.getMessage());
    }

    @Test
    public void testRetirerEleve() throws ExceptionMauvaisIdChapitre, ExceptionMauvaisIdEleve {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Eleve eleveTest = mock(Eleve.class);

        cours.ajouterEleve(eleveTest);
        when(eleveTest.getId()).thenReturn(8);
        // Act
        Eleve eleveRetire = cours.supprimerEleve(8);

        // Assert
        assertEquals(eleveTest, eleveRetire);
        assertFalse(cours.getEleves().contains(eleveTest));
        assertTrue(cours.getEleves().isEmpty());
    }

    @Test
    public void testRetirerEleveNonExistant()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Eleve eleveTest = mock(Eleve.class);

        // Act & Assert
        when(eleveTest.getId()).thenReturn(6);

        assertThrows(ExceptionMauvaisIdEleve.class, ()->cours.supprimerEleve(3));
        assertThrows(ExceptionMauvaisIdEleve.class, ()->cours.supprimerEleve(-1));
    }

    @Test
    public void testAjouterUneCategorieDejaPresente() throws ExceptionCategorieDejaPresente
    {
        // Arrange
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        Categorie categorieTest = mock(Categorie.class);
        Categorie categorieTest2 = mock(Categorie.class);
        categories.add(categorieTest);

        Cours cours = new Cours("unTitre", "uneDescription", false, categories, Difficulte.DEBUTANT, new Createur());

        when(categorieTest.getLabel()).thenReturn(Categorie.DEVELOPPEMENT_MOBILE.getLabel());
        when(categorieTest2.getLabel()).thenReturn(Categorie.DEVELOPPEMENT_MOBILE.getLabel());

        // Act & Assert
        assertThrows(ExceptionCategorieDejaPresente.class, ()->cours.ajouterCategorie(categorieTest2));
        assertFalse(cours.getCategories().contains(categorieTest2));
    }

    @Test
    public void testRetirerUneCategorieSurUneListeVide()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Categorie categorieTest = Categorie.MATHEMATIQUES;

        // Act & Assert
        assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie(categorieTest.getLabel()));
    }

    @Test
    public void testRetirerCategorieAvecSensibiliteCasse()
    {
        // Arrange
        Categorie categorieTest = Categorie.MATHEMATIQUES;
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        categories.add(categorieTest);
        Cours cours = new Cours("unTitre", "uneDescription", false, categories, Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie("mathematiques"));
        assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie("MATHEMATIQUES"));
        assertThrows(ExceptionMauvaisLabelCategorie.class, ()->cours.supprimerCategorie("mAthEmAtIques"));
        assertTrue(cours.getCategories().contains(categorieTest));
    }

    @Test
    public void testAjouterUnEleveDejaInscrit()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Eleve eleveTest = mock(Eleve.class);
        Eleve eleveTest2 = mock(Eleve.class);

        when(eleveTest.getId()).thenReturn(9);
        when(eleveTest2.getId()).thenReturn(9);

        cours.ajouterEleve(eleveTest);

        // Act & Assert
        assertThrows(ExceptionEleveDejaPresent.class, ()->cours.ajouterEleve(eleveTest2));
        assertFalse(cours.getEleves().contains(eleveTest2));
    }

    @Test
    public void testRetirerUnEleveSurUneListeVide()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(ExceptionMauvaisIdEleve.class, ()->cours.supprimerEleve(91));
    }

    @Test
    public void testRetirerUnChapitreSurUneListeVide()
    {
        // Arrange
        Cours cours = new Cours("unTitre", "uneDescription", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        // Act & Assert
        assertThrows(ExceptionMauvaisIdChapitre.class, ()->cours.retirerChapitre(18));
    }
}
