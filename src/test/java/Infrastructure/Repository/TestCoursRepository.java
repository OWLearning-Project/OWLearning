package Infrastructure.Repository;

import Domain.Models.*;
import Domain.Ports.IRepository.ICoursRepository;
import Infrastructure.Persistence.Interface.JpaCoursRepository;
import Infrastructure.Persistence.Repository.CoursRepository;
import Shared.Exceptions.ExceptionCategorieDejaPresente;
import Shared.Exceptions.ExceptionMauvaisIdChapitre;
import Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCoursRepository {
    @Mock
    private JpaCoursRepository repositoryJpa;

    @InjectMocks
    private CoursRepository repository;

    @Test
    public void coursTrouveParId() {
        // Arrange
        Cours coursAttendu = new Cours();

        when(repositoryJpa.findByIdNative(anyInt())).thenReturn(coursAttendu);

        // Act
        Cours coursTrouve = repository.trouverParId(93);

        // Assert
        assertEquals(coursAttendu, coursTrouve);
        assertNotNull(coursTrouve);
        verify(repositoryJpa, times(1)).findByIdNative(anyInt());
    }

    @Test
    public void coursTrouveParIdNull() {
        // Arrange
        when(repositoryJpa.findByIdNative(anyInt())).thenReturn(null);

        // Act
        Cours coursTrouve = repository.trouverParId(928);

        // Assert
        assertNull(coursTrouve);
        verify(repositoryJpa, times(1)).findByIdNative(anyInt());
    }

    @Test
    public void coursCreesSansFiltre() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdCreateurNative(isNull(), anyInt(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur(null, 39, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdCreateurNative(isNull(), anyInt(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursCreesAvecFiltreUnique() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdCreateurNative(anyString(), anyInt(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur("développement", 23, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdCreateurNative(anyString(), anyInt(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursCreesAvecFiltreMultiple() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdCreateurNative(anyString(), anyInt(), isNull(), isNull(), anyBoolean())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur("développement", 21, null, null, true);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdCreateurNative(anyString(), anyInt(), isNull(), isNull(), anyBoolean());
    }

    @Test
    public void aucunCoursCreesAvecFiltre() {
        // Arrange
        when(repositoryJpa.findByIdCreateurNative(anyString(), anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur("unTitre", 10, "uneDifficulte", "uneCategorie", true);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByIdCreateurNative(anyString(), anyInt(), anyString(), anyString(), anyBoolean());
    }

    @Test
    public void coursTrouvesSansFiltre() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findAllCoursPubliesNative(isNull(), isNull(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursFiltre(null, null, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findAllCoursPubliesNative(isNull(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursTrouvesAvecFiltreUnique() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findAllCoursPubliesNative(anyString(), isNull(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursFiltre("développement", null, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findAllCoursPubliesNative(anyString(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursTrouvesAvecFiltreMultiple() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findAllCoursPubliesNative(anyString(), isNull(), isNull(), isNull(), anyBoolean())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursFiltre("développement", null, null, null, true);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findAllCoursPubliesNative(anyString(), isNull(), isNull(), isNull(), anyBoolean());
    }

    @Test
    public void aucunCoursTrouveAvecFiltre() {
        // Arrange
        when(repositoryJpa.findAllCoursPubliesNative(anyString(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursFiltre("unTitre", "unCreateur", "uneDifficulte", "uneCategorie", true);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findAllCoursPubliesNative(anyString(), anyString(), anyString(), anyString(), anyBoolean());
    }

    @Test
    public void coursInscritsSansFiltre() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdEleveNative(anyInt(), isNull(), isNull(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(23, null, null, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdEleveNative(anyInt(), isNull(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursInscritsAvecFiltreUnique() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdEleveNative(anyInt(), anyString(), isNull(), isNull(), isNull(), isNull())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(23, "développement", null, null, null, null);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdEleveNative(anyInt(), anyString(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    public void coursInscritsAvecFiltreMultiple() {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByIdEleveNative(anyInt(), anyString(), isNull(), isNull(), isNull(), anyBoolean())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(19, "développement", null, null, null, true);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByIdEleveNative(anyInt(), anyString(), isNull(), isNull(), isNull(), anyBoolean());
    }

    @Test
    public void aucunCoursInscritsAvecFiltre() {
        // Arrange
        when(repositoryJpa.findByIdEleveNative(anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(34, "unTitre", "unCreateur", "uneDifficulte", "uneCategorie", true);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByIdEleveNative(anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean());
    }

    @Test
    public void TestAjoutChapitreReussi() {
        //Arrange
        int coursId = 1;
        Chapitre chapitreTest = new Chapitre();
        chapitreTest.setTitre("chapitre test");

        Cours coursTest = new Cours("Titre", "Desc", false, new ArrayList<>(), Difficulte.DEBUTANT, new Createur());
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act
        repository.ajouterChapitre(coursId, chapitreTest);

        //Assert
        assertTrue(coursTest.getChapitres().contains(chapitreTest));
        verify(repositoryJpa).save(coursTest);
    }

    @Test
    public void TestAjoutChapitreCoursInexistant(){
        //Arrange
        int coursId = 55;
        Chapitre chaptest = new Chapitre();

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.empty());

        //Act
        repository.ajouterChapitre(coursId, chaptest);

        //Assert
        verify(repositoryJpa, never()).save(any());

    }

    @Test
    public void TestAjouterChapitreCoursInexistant(){
        //Arrange
        int coursId = 5;
        Chapitre chapitreTest = new Chapitre();
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.empty());

        //Act
        repository.ajouterChapitre(coursId, chapitreTest);

        //Assert
        verify(repositoryJpa, never()).save(any());
    }

    @Test
    public void TestSuppChapitreReussi() throws ExceptionMauvaisIdChapitre {
        //Arrange
        int coursId = 6;
        Cours coursTest = new Cours("Titre", "Desc", false, new ArrayList<>(), Difficulte.INTERMEDIAIRE, new Createur());

        Chapitre chapitreTest = new Chapitre();
        coursTest.ajouterChapitre(chapitreTest);
        int idSuppression = chapitreTest.getId();

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act
        Chapitre resultat = repository.retirerChapitre(coursId, idSuppression);

        //Assert
        assertNotNull(resultat);
        assertTrue(coursTest.getChapitres().isEmpty());
        verify(repositoryJpa).save(coursTest);
    }

    @Test
    public void TestSuppChapitreCoursInexistant() throws ExceptionMauvaisIdChapitre {
        //Arrange
        int coursId = 95;
        int chapId = 1;

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.empty());

        //Act
        Chapitre resultat = repository.retirerChapitre(coursId, chapId);

        //Assert
        assertNull(resultat);
        verify(repositoryJpa, never()).save(any());
    }

    @Test
    public void TestSuppChapitreIdInexistant(){
        //Arrange
        int coursId = 8;
        Cours coursTest = new Cours("Titre", "Desc", false, new ArrayList<>(), Difficulte.INTERMEDIAIRE, new Createur());
        Chapitre chapitreTest = new Chapitre();
        coursTest.ajouterChapitre(chapitreTest);

        int idInexistant = 9;

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act et Assert
        assertThrows(ExceptionMauvaisIdChapitre.class, () -> {
            repository.retirerChapitre(coursId, idInexistant);
        });
        verify(repositoryJpa, never()).save(any());
    }

    @Test
    public void TestModifieDifficulteReussi() {
        //Arrange
        int coursId = 10;
        Difficulte diffTest = Difficulte.AVANCE;

        Cours coursTest = new Cours("Titre", "Desc", false, new ArrayList<>(), Difficulte.INTERMEDIAIRE, new Createur());
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act
        repository.modifierDifficulteCours(coursId, diffTest);

        //Assert
        assertEquals(diffTest, coursTest.getDifficulte());
        verify(repositoryJpa).save(coursTest);
    }

    @Test
    public void TestModifieDifficulteCoursInexistant(){
        //Arrange
        int coursId = 25;
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.empty());

        //Act
        repository.modifierDifficulteCours(coursId, Difficulte.AVANCE);

        //Assert
        verify(repositoryJpa, never()).save(any());
    }

    @Test
    public void TestAjouterCategorieReussi() {
        //Arrange
        int coursId = 63;

        Cours coursTest = new Cours("test", "Desc", false, new ArrayList<>(), Difficulte.INTERMEDIAIRE, new Createur());
        Categorie nouvelleCategorie = Categorie.IA_DATASCIENCES;
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act
        repository.ajouterCategorieCours(coursId, nouvelleCategorie);

        //Assert
        assertTrue(coursTest.getCategories().contains(nouvelleCategorie));
        verify(repositoryJpa).save(coursTest);
    }

    @Test
    public void TestAjouterCategorieDejaPresente() throws ExceptionCategorieDejaPresente {
        int coursId = 9;
        Cours coursTest = new Cours("test", "Desc", false, new ArrayList<>(), Difficulte.INTERMEDIAIRE, new Createur());
        Categorie categorieAjouter = Categorie.HISTOIRE_INFORMATIQUE;
        coursTest.ajouterCategorie(categorieAjouter);

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act et Assert
        assertThrows(ExceptionCategorieDejaPresente.class, () -> {
            repository.ajouterCategorieCours(coursId, categorieAjouter);
        });
        verify(repositoryJpa, times(0)).save(any());
    }

    @Test
    public void TestSupprimerCategorieReussi() throws ExceptionMauvaisLabelCategorie {
        //Arrange
        int coursId = 8;
        Cours coursTest = new Cours("test", "Desc", false, new ArrayList<>(), Difficulte.AVANCE, new Createur());
        Categorie supprimerCategorie = Categorie.DEVELOPPEMENT_MOBILE;

        coursTest.ajouterCategorie(supprimerCategorie);
        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act
        Categorie resultat = repository.supprimerCategorieCours(coursId, supprimerCategorie);

        //Assert
        assertNotNull(resultat);
        assertFalse(coursTest.getCategories().contains(supprimerCategorie));
        verify(repositoryJpa).save(coursTest);
    }

    @Test
    public void TestSupprimerCategorieInexistante() throws ExceptionMauvaisLabelCategorie {
        //Arrange
        int coursId = 8;
        Cours coursTest = new Cours("test", "Desc", false, new ArrayList<>(), Difficulte.AVANCE, new Createur());
        Categorie supprimerCategorie = Categorie.DEVELOPPEMENT_MOBILE;

        when(repositoryJpa.findById(coursId)).thenReturn(Optional.of(coursTest));

        //Act et Assert
        assertThrows(ExceptionMauvaisLabelCategorie.class, () -> {
            repository.supprimerCategorieCours(coursId, supprimerCategorie);
        });
    }
}