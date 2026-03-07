package Application.Service;

import app.OwLearning.Application.Services.ServiceCours;
import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.apache.commons.lang3.builder.Diff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceCours
{
    @Mock
    private ICoursRepository coursRepository;

    @InjectMocks
    private ServiceCours serviceCours;


    @Test
    public void coursRecupereParId()
    {
        // Arrange
        Cours cours = new Cours("Base de donnée", "", true, new ArrayList<Categorie>(), Difficulte.AVANCE, new Createur());

        when(coursRepository.trouverParId(anyInt())).thenReturn(cours);

        // Act
        Cours coursRetourne = serviceCours.getCoursParId(34);

        // Assert
        assertEquals(cours, coursRetourne);
        assertNotNull(coursRetourne);
        verify(coursRepository, times(1)).trouverParId(anyInt());
    }

    @Test
    public void pasDeCoursAssocieParId()
    {
        // Arrange
        when(coursRepository.trouverParId(34)).thenThrow(new ExceptionCoursInexistant("le cours n'existe pas", 34));

        // Act & Assert
        assertThrows(ExceptionCoursInexistant.class, () -> serviceCours.getCoursParId(34));
        verify(coursRepository, times(1)).trouverParId(34);
    }

    @Test
    public void lesCoursCreesParIdCreateurSontRecuperes()
    {
        // Arrange
        Cours cours1 = new Cours("Programmation système", "", true, new ArrayList<Categorie>(), Difficulte.INTERMEDIAIRE, new Createur());
        Cours cours2 = new Cours("Développement web", "", true, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        ArrayList<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverParIdCreateur(38)).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournees = serviceCours.getCoursCrees(38);

        // Assert
        assertEquals(listeCours, listeCoursRetournees);
        assertNotNull(listeCoursRetournees);
        verify(coursRepository, times(1)).trouverParIdCreateur(38);
    }

    @Test
    public void pasDeCoursCreesParLeCreateur()
    {
        // Arrange
        when(coursRepository.trouverParIdCreateur(93)).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursCrees(93);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverParIdCreateur(93);
    }

    @Test
    public void lesCoursOuEleveEstInscritSontRecuperes()
    {
        // Arrange
        Cours cours1 = new Cours("Programmation système", "", true, new ArrayList<Categorie>(), Difficulte.INTERMEDIAIRE, new Createur());
        Cours cours2 = new Cours("Développement web", "", true, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        ArrayList<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverParIdEleve(21)).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournees = serviceCours.getCoursInscrits(21);

        // Assert
        assertEquals(listeCours, listeCoursRetournees);
        assertNotNull(listeCoursRetournees);
        verify(coursRepository, times(1)).trouverParIdEleve(21);
    }

    @Test
    public void pasDeCoursInscritsParLeCreateur()
    {
        // Arrange
        when(coursRepository.trouverParIdEleve(95)).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursInscrits(95);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverParIdEleve(95);
    }

    @Test
    public void recupererLesCoursPublies()
    {
        // Arrange
        Cours cours1 = new Cours("Développement web", "", true, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Cours cours2 = new Cours("Communication", "", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());
        Cours cours3 = new Cours("Management", "", true, new ArrayList<Categorie>(), Difficulte.INTERMEDIAIRE, new Createur());
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        listeAttendues.add(cours1);
        listeAttendues.add(cours2);
        listeAttendues.add(cours3);

        when(coursRepository.trouverCoursPublies()).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = serviceCours.getCoursPublies();

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(coursRepository, times(1)).trouverCoursPublies();
    }

    @Test
    public void creerCours()
    {
        // Arrange
        String titre = "Java";
        String description = "Cours Java";
        Difficulte difficulte = Difficulte.DEBUTANT;
        int createurId = 1;
        Createur createur = new Createur();

        Cours coursCree = new Cours(titre, description, false, new ArrayList<Categorie>(), difficulte, createur);
        when(coursRepository.creerCours(titre, description, difficulte, createurId)).thenReturn(coursCree);

        // Act
        Cours resultat = serviceCours.creerCours(titre, description, difficulte, createurId);

        // Assert
        assertThat(resultat).isSameAs(coursCree);
        verify(coursRepository).creerCours(titre, description, difficulte, createurId);
        verifyNoMoreInteractions(coursRepository);
    }

    @Test
    public void creerCoursAvecTitreVide()
    {
        // Act + Assert
        assertThatThrownBy(() -> serviceCours.creerCours("", "desc", Difficulte.DEBUTANT, 1))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(coursRepository);
    }

    @Test
    public void publierCours()
    {
        // Arrange
        int coursId = 10;
        Cours cours = new Cours();
        when(coursRepository.coursExiste(coursId)).thenReturn(true);
        when(coursRepository.trouverParId(coursId)).thenReturn(cours);

        // Act
        serviceCours.publierCours(coursId);

        // Assert
        assertTrue(cours.getEstPublie());
        verify(coursRepository).coursExiste(coursId);
        verify(coursRepository).trouverParId(coursId);
        verify(coursRepository).sauvegarder(any(Cours.class));
        verifyNoMoreInteractions(coursRepository);
    }

    @Test
    public void publierCoursIdNonValide()
    {
        // Arrange
        int coursId = 0;

        // Act + Assert
        assertThatThrownBy(() -> serviceCours.publierCours(coursId))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(coursRepository);
    }

    @Test
    public void publierCoursInexistant()
    {
        // Arrange
        int coursId = 23;
        when(coursRepository.coursExiste(coursId)).thenReturn(false);

        // Act + Assert
        assertThrows(ExceptionCoursInexistant.class, () -> serviceCours.publierCours(coursId));
        verify(coursRepository, times(1)).coursExiste(coursId);
    }

    @Test
    void modifierInformationsCours()
    {
        // Arrange
        int coursId = 5;
        String titre = "Nouveau titre";
        String description = "Nouvelle description";
        Difficulte difficulte = Difficulte.DEBUTANT;
        boolean estPrive = true;

        Cours cours = new Cours();

        when(coursRepository.coursExiste(coursId)).thenReturn(true);
        when(coursRepository.trouverParId(coursId)).thenReturn(cours);

        // Act
        serviceCours.modifierInformationsCours(coursId, titre, description, difficulte, estPrive);

        // Assert
        assertEquals(titre, cours.getTitre());
        assertEquals(description, cours.getDescription());
        assertEquals(difficulte, cours.getDifficulte());
        assertEquals(estPrive, cours.getEstPrive());

        verify(coursRepository).trouverParId(coursId);
        verify(coursRepository).sauvegarder(cours);
        verifyNoMoreInteractions(coursRepository);
    }

    @Test
    void modifierInformationsCoursDescriptionVide()
    {
        // Arrange
        int coursId = 5;

        // Act + Assert
        assertThatThrownBy(() -> serviceCours.modifierInformationsCours(coursId, "titre", "", Difficulte.DEBUTANT, true))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(coursRepository);
    }

    @Test
    void supprimerCours()
    {
        // Arrange
        int coursId = 3;
        Cours coursSupprime = new Cours();
        when(coursRepository.supprimerCours(coursId)).thenReturn(coursSupprime);

        // Act
        Cours resultat = serviceCours.supprimerCours(coursId);

        // Assert
        assertThat(resultat).isSameAs(coursSupprime);
        verify(coursRepository).supprimerCours(coursId);
        verifyNoMoreInteractions(coursRepository);
    }

    @Test
    void supprimerCoursIdNonValide()
    {
        // Arrange
        int coursId = 0;

        // Act + Assert
        assertThatThrownBy(() -> serviceCours.supprimerCours(coursId))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(coursRepository);
    }

    @Test
    public void TestAjouterChapitreValide()
    {
        //Arrange
        int coursId = 5;
        ArrayList<Ressource> ressourceTest = new ArrayList<>();
        Chapitre chapitreTest = new Chapitre("test", "je suis le test", ressourceTest);
        Cours coursTest = new Cours("test", "", false, new ArrayList<Categorie>(), Difficulte.AVANCE, new Createur());

        when(coursRepository.coursExiste(coursId)).thenReturn(true);
        when(coursRepository.trouverParId(coursId)).thenReturn(coursTest);

        //Act
        serviceCours.ajouterChapitre(coursId, chapitreTest);

        //Assert
        verify(coursRepository, times(1)).sauvegarder(coursTest);
        verify(coursRepository, times(1)).trouverParId(coursId);
        verify(coursRepository, times(1)).coursExiste(coursId);
        assertTrue(coursTest.getChapitres().contains(chapitreTest));
        assertEquals(coursTest, chapitreTest.getCours());
    }

    @Test
    public void TestAjouterChapitreNull()
    {
        //Arrange
        int coursId = 4;
        Chapitre chapitreTestNull = null;

        //Act et Assert
        assertThrows(IllegalArgumentException.class, ()-> {
            serviceCours.ajouterChapitre(coursId, chapitreTestNull);
        });
        verifyNoInteractions(coursRepository);
    }

    @Test
    public void TestAjouterChapitreCoursInexistant()
    {
        //Arrange
        Chapitre chapitreTest = new Chapitre();
        int coursIdInexistant = 85;

        when(coursRepository.coursExiste(coursIdInexistant)).thenReturn(false);

        //Act et Assert
        assertThrows(ExceptionCoursInexistant.class, () -> {
            serviceCours.ajouterChapitre(coursIdInexistant, chapitreTest);
        });
        verify(coursRepository, never()).sauvegarder(any());
    }

    @Test
    public void TestRetirerChapitreExistant() throws ExceptionMauvaisIdChapitre
    {
        //Arrange
        int coursId = 9;
        int chapitreId = 17;

        Cours cours = new Cours("Test", "Description", false, new ArrayList<Categorie>(), Difficulte.DEBUTANT, new Createur());

        Chapitre chapitreARetirer = mock(Chapitre.class);
        when(chapitreARetirer.getId()).thenReturn(chapitreId);

        cours.ajouterChapitre(chapitreARetirer);

        when(coursRepository.coursExiste(coursId)).thenReturn(true);
        when(coursRepository.trouverParId(coursId)).thenReturn(cours);

        //Act
        serviceCours.retirerChapitre(coursId, chapitreId);

        //Assert
        assertEquals(0, cours.getChapitres().size());
        verify(coursRepository, times(1)).coursExiste(coursId);
        verify(coursRepository, times(1)).trouverParId(coursId);
        verify(coursRepository, times(1)).sauvegarder(cours);
    }

    @Test
    public void TestRetirerChapitreInvalide()
    {
        //Arrange
        int coursId = -9;
        int chapitreIdInvalide = -15;

        //Act et Assert
        assertThrows(IllegalArgumentException.class, ()-> {
            serviceCours.retirerChapitre(coursId, chapitreIdInvalide);
        });
        verifyNoInteractions(coursRepository);
    }

    @Test
    public void TestRetirerChapitreCoursInexistant() throws ExceptionCoursInexistant
    {
        //Arrange
        int coursId = 5;
        int chapitreId = 12;

        when(coursRepository.coursExiste(coursId)).thenReturn(false);

        //Act + Assert
        assertThrows(ExceptionCoursInexistant.class, () -> serviceCours.retirerChapitre(coursId, chapitreId));
        verify(coursRepository).coursExiste(coursId);
    }

    @Test
    public void TestRetirerChapitreInexistant()
    {
        //Arrange
        int coursId = 5;
        int chapitreId = -23;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> serviceCours.retirerChapitre(coursId, chapitreId));
    }

    @Test
    public void TestAjouterCategorieCoursInexistant()
    {
        //Arrange
        int coursIdInexistant = 555;

        when(coursRepository.trouverParId(coursIdInexistant)).thenReturn(null);

        //Act et Assert
        Exception exception = assertThrows(ExceptionCoursInexistant.class, () -> {
            serviceCours.ajouterCategorieCours(coursIdInexistant, Categorie.DEVELOPPEMENT_MOBILE);
        });
        assertEquals("le cours n'existe pas", exception.getMessage());
        verify(coursRepository, times(1)).trouverParId(coursIdInexistant);
    }

    @Test
    public void TestSupprimerCategorieValide() throws ExceptionMauvaisLabelCategorie
    {
        //Arrange
        int coursId = 7;
        ArrayList<Categorie> listeCategories = new ArrayList<>();
        Categorie categorieSupprimer = Categorie.IA_DATASCIENCES;
        Categorie categoriePasSupprimer = Categorie.DEVELOPPEMENT_WEB;
        listeCategories.add(categorieSupprimer);
        listeCategories.add(categoriePasSupprimer);
        Cours coursTest = new Cours("cours test", "cours qui sert de test", false, listeCategories, Difficulte.AVANCE, new Createur());

        when(coursRepository.trouverParId(coursId)).thenReturn(coursTest);

        //Act
        serviceCours.supprimerCategorieCours(coursId, Categorie.IA_DATASCIENCES);

        //Assert
        assertTrue(coursTest.getCategories().contains(categoriePasSupprimer));
        assertFalse(coursTest.getCategories().contains(categorieSupprimer));
        assertEquals(1, coursTest.getCategories().size());
        verify(coursRepository).trouverParId(coursId);
    }
}
