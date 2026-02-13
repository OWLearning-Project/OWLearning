package Infrastructure.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaCoursRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.CoursRepository;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
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

        when(repositoryJpa.findById(anyInt())).thenReturn(Optional.of(coursAttendu));

        // Act
        Cours coursTrouve = repository.trouverParId(93);

        // Assert
        assertEquals(coursAttendu, coursTrouve);
        assertNotNull(coursTrouve);
        verify(repositoryJpa, times(1)).findById(anyInt());
    }

    @Test
    public void coursTrouveParIdNull() {
        // Arrange
        when(repositoryJpa.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExceptionCoursInexistant.class, () -> repository.trouverParId(82));
        verify(repositoryJpa, times(1)).findById(anyInt());
    }

    @Test
    public void rechercheCoursCreesParCreateur()
    {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByCreateurIdUtilisateur(anyInt())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur(39);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByCreateurIdUtilisateur(anyInt());
    }

    @Test
    public void createurACreeAucunCours()
    {
        // Arrange
        when(repositoryJpa.findByCreateurIdUtilisateur(anyInt())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdCreateur(10);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByCreateurIdUtilisateur(anyInt());
    }

    @Test
    public void rechercheCoursPublies()
    {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByEstPublieTrue()).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursPublies();

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByEstPublieTrue();
    }

    @Test
    public void aucunCoursPublieTrouve()
    {
        // Arrange
        when(repositoryJpa.findByEstPublieTrue()).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverCoursPublies();

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByEstPublieTrue();
    }

    @Test
    public void rechercheCoursOuEstIncritEleve()
    {
        // Arrange
        ArrayList<Cours> listeAttendues = new ArrayList<Cours>();

        when(repositoryJpa.findByElevesIdUtilisateur(anyInt())).thenReturn(listeAttendues);

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(23);

        // Assert
        assertEquals(listeAttendues, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryJpa, times(1)).findByElevesIdUtilisateur(anyInt());
    }

    @Test
    public void eleveInscritAAucunCours()
    {
        // Arrange
        when(repositoryJpa.findByElevesIdUtilisateur(anyInt())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(34);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByElevesIdUtilisateur(anyInt());
    }
}