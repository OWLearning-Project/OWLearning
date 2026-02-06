package Infrastructure.Repository;

import Domain.Models.Cours;
import Infrastructure.Persistence.Interface.JpaCoursRepository;
import Infrastructure.Persistence.Repository.CoursRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

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
    public void coursCreesSansFiltre()
    {
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
    public void coursCreesAvecFiltreUnique()
    {
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
    public void coursCreesAvecFiltreMultiple()
    {
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
    public void aucunCoursCreesAvecFiltre()
    {
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
    public void coursTrouvesSansFiltre()
    {
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
    public void coursTrouvesAvecFiltreUnique()
    {
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
    public void coursTrouvesAvecFiltreMultiple()
    {
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
    public void aucunCoursTrouveAvecFiltre()
    {
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
    public void coursInscritsSansFiltre()
    {
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
    public void coursInscritsAvecFiltreUnique()
    {
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
    public void coursInscritsAvecFiltreMultiple()
    {
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
    public void aucunCoursInscritsAvecFiltre()
    {
        // Arrange
        when(repositoryJpa.findByIdEleveNative(anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeRecuperee = repository.trouverParIdEleve(34,"unTitre", "unCreateur", "uneDifficulte", "uneCategorie", true);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeRecuperee);
        assertEquals(0, listeRecuperee.size());
        verify(repositoryJpa, times(1)).findByIdEleveNative(anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean());
    }
}