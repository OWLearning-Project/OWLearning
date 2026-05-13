package Application.Service;

import app.OwLearning.Application.Services.ServiceInscription;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Exceptions.ExceptionMauvaisIdEleve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestServiceInscription
{
    @Mock
    private ICoursRepository coursRepository;

    @Mock
    private IUtilisateurRepository utilisateurRepository;

    @InjectMocks
    private ServiceInscription serviceInscription;

    @Test
    public void inscrireEtudiant()
    {
        // Arrange
        int idEtudiant = 1;
        int idCours = 2;

        Cours mockCours = mock(Cours.class);
        Eleve mockEleve = mock(Eleve.class);

        when(coursRepository.trouverParId(idCours)).thenReturn(mockCours);
        when(utilisateurRepository.trouverParId(idEtudiant)).thenReturn(mockEleve);
        when(mockCours.getId()).thenReturn(idCours);

        // Act
        int resultat = serviceInscription.inscrireEtudiant(idEtudiant, idCours);

        // Assert
        assertEquals(idCours, resultat);
        verify(mockCours, times(1)).ajouterEleve(mockEleve);
        verify(coursRepository, times(1)).sauvegarder(mockCours);
    }

    @Test
    public void inscrireEtudiantAvecUnIdNonValide()
    {
        // Arrange
        int idEtudiant = 0;
        int idCours = 2;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.inscrireEtudiant(idEtudiant, idCours));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void inscrireEtudiantAvecUnIdCoursInvalide()
    {
        // Arrange
        int idCours = 0;
        int idEtudiant = 1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.inscrireEtudiant(idCours, idEtudiant));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void supprimerInscriptionCours() throws ExceptionMauvaisIdEleve {
        // Arrange
        int idCours = 2;
        int idEtudiant = 1;

        Cours mockCours = mock(Cours.class);
        when(coursRepository.trouverParId(idCours)).thenReturn(mockCours);

        // Act
        serviceInscription.supprimerInscriptionCours(idCours, idEtudiant);

        // Assert
        verify(mockCours,times(1)).supprimerEleve(idEtudiant);
        verify(coursRepository, times(1)).sauvegarder(mockCours);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void supprimerInscriptionCoursAvecUnIdEtudiantNonValide()
    {
        // Arrange
        int idCours = 2;
        int idEtudiant = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.supprimerInscriptionCours(idCours, idEtudiant));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void getEtudiantsInscrits()
    {
        // Arrange
        int idCours = 2;
        Cours mockCours = mock(Cours.class);

        List<Eleve> eleves = new ArrayList<>();
        Eleve mockEleve = mock(Eleve.class);
        eleves.add(mockEleve);

        when(coursRepository.trouverParId(idCours)).thenReturn(mockCours);
        when(mockCours.getEleves()).thenReturn(eleves);

        // Act
        ArrayList<Utilisateur> resultat = serviceInscription.getEtudiantsInscrits(idCours);

        // Assert
        assertNotNull(resultat);
        assertEquals(1,resultat.size());
        assertTrue(resultat.contains(mockEleve));

        verify(coursRepository, times(1)).trouverParId(idCours);
        verify(mockCours, times(2)).getEleves();
    }

    @Test
    public void getEtudiantsInscritsAvecIdCoursNonValide()
    {
        // Arrange
        int idCours = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                serviceInscription.getEtudiantsInscrits(idCours)
        );

        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }
}

