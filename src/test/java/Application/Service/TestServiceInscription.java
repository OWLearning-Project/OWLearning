package Application.Service;

import Application.Services.ServiceInscription;
import Domain.Models.Cours;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.ICoursRepository;
import Domain.Ports.IRepository.IUtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

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

        when(coursRepository.inscrireEtudiant(idEtudiant, idCours)).thenReturn(1);

        // Act
        int resultat = serviceInscription.inscrireEtudiant(idEtudiant, idCours);

        // Assert
        assertEquals(1, resultat);
        verify(coursRepository).inscrireEtudiant(idEtudiant, idCours);
        verifyNoMoreInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
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
    public void getInscriptionsEtudiant()
    {
        // Arrange
        int idEtudiant = 1;
        ArrayList<Cours> liste = new ArrayList<>();
        liste.add(new Cours());
        when(coursRepository.trouverInscriptionsEtudiant(idEtudiant)).thenReturn(liste);

        // Act
        ArrayList<Cours> resultat = serviceInscription.getInscriptionsEtudiant(idEtudiant);

        // Assert
        assertSame(liste, resultat);
        verify(coursRepository).trouverInscriptionsEtudiant(idEtudiant);
        verifyNoMoreInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void getInscriptionsEtudiantAvecUnIdNonValide()
    {
        // Arrange
        int idEtudiant = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.getInscriptionsEtudiant(idEtudiant));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void validerInscription()
    {
        // Arrange
        int idCours = 2;
        int idEtudiant = 1;
        doNothing().when(coursRepository).validerInscription(idCours, idEtudiant);

        // Act
        serviceInscription.validerInscription(idCours, idEtudiant);

        // Assert
        verify(coursRepository).validerInscription(idCours, idEtudiant);
        verifyNoMoreInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void validerInscriptionAvecUnIdCoursInvalide()
    {
        // Arrange
        int idCours = 0;
        int idEtudiant = 1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.validerInscription(idCours, idEtudiant));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void refuserInscription()
    {
        // Arrange
        int idCours = 2;
        int idEtudiant = 1;
        doNothing().when(coursRepository).refuserInscription(idCours, idEtudiant);

        // Act
        serviceInscription.refuserInscription(idCours, idEtudiant);

        // Assert
        verify(coursRepository).refuserInscription(idCours, idEtudiant);
        verifyNoMoreInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void refuserInscriptionAvecUnIdNonValide()
    {
        // Arrange
        int idCours = 2;
        int idEtudiant = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceInscription.refuserInscription(idCours, idEtudiant));
        verifyNoInteractions(coursRepository);
        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void getEtudiantsInscrits()
    {
        // Arrange
        int idCours = 2;
        ArrayList<Utilisateur> liste = new ArrayList<>();
        liste.add(new Utilisateur());

        when(utilisateurRepository.trouverEtudiantsInscrits(idCours)).thenReturn(liste);

        // Act
        ArrayList<Utilisateur> resultat = serviceInscription.getEtudiantsInscrits(idCours);

        // Assert
        assertSame(liste, resultat);
        verify(utilisateurRepository).trouverEtudiantsInscrits(idCours);
        verifyNoMoreInteractions(utilisateurRepository);
        verifyNoInteractions(coursRepository);
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

