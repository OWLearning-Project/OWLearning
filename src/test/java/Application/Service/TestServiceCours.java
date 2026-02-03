package Application.Service;

import Application.Services.ServiceCours;
import Domain.Models.Cours;
import Domain.Ports.IRepository.ICoursRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceCours
{
    @Mock
    private ICoursRepository coursRepository;

    @InjectMocks
    private ServiceCours serviceCours;

    @Test
    public void tousLesCoursSontRecuperes()
    {
        // Arrange
        Cours cours1 = new Cours();
        Cours cours2 = new Cours();
        List<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverTousLesCours()).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getTousLesCours();

        // Assert
        assertEquals(new ArrayList<>(listeCours), listeCoursRetournee);
        assertNotNull(listeCoursRetournee);
        verify(coursRepository, times(1)).trouverTousLesCours();
    }

    @Test
    public void pasDeCoursDansLaBd()
    {
        // Arrange
        when(coursRepository.trouverTousLesCours()).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getTousLesCours();

        // Assert
        assertEquals(new ArrayList<>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverTousLesCours();
    }

    @Test
    public void lesCoursSontRecuperesParTitre()
    {
        // Arrange
        Cours cours1 = new Cours();
        Cours cours2 = new Cours();
        List<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverParTitre(any(String.class))).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursParTitre("unTitre");

        // Assert
        assertEquals(new ArrayList<>(listeCours), listeCoursRetournee);
        assertNotNull(listeCoursRetournee);
        verify(coursRepository, times(1)).trouverParTitre(any(String.class));
    }

    @Test
    public void pasDeCoursAvecTitreDansLaBd()
    {
        // Arrange
        when(coursRepository.trouverParTitre(any(String.class))).thenReturn(new ArrayList<>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursParTitre("unTitre");

        // Assert
        assertEquals(new ArrayList<>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverParTitre(any(String.class));
    }

    @Test
    public void lesCoursSontRecuperesParId()
    {
        // Arrange
        Cours cours = new Cours();

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
        when(coursRepository.trouverParId(anyInt())).thenReturn(null);

        // Act
        Cours coursRetourne = serviceCours.getCoursParId(34);

        // Assert
        assertNull(coursRetourne);
        verify(coursRepository, times(1)).trouverParId(anyInt());
    }

    @Test
    public void lesCoursPubliesSontRecuperes()
    {
        // Arrange
        Cours cours1 = new Cours();
        Cours cours2 = new Cours();
        List<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverParIdCreateur(anyInt())).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournees = serviceCours.getCoursPublies(38);

        // Assert
        assertEquals(listeCours, listeCoursRetournees);
        assertNotNull(listeCoursRetournees);
        verify(coursRepository, times(1)).trouverParIdCreateur(anyInt());
    }

    @Test
    public void leCreateurAPasDeCoursPublies()
    {
        // Arrange
        when(coursRepository.trouverParIdCreateur(anyInt())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursPublies(93);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverParIdCreateur(anyInt());
    }

    @Test
    public void lesCoursInscritsSontRecuperes()
    {
        // Arrange
        Cours cours1 = new Cours();
        Cours cours2 = new Cours();
        List<Cours> listeCours = new ArrayList<Cours>();

        listeCours.add(cours1);
        listeCours.add(cours2);

        when(coursRepository.trouverParIdEleve(anyInt())).thenReturn(listeCours);

        // Act
        ArrayList<Cours> listeCoursRetournees = serviceCours.getCoursInscrits(23);

        // Assert
        assertEquals(listeCours, listeCoursRetournees);
        assertNotNull(listeCoursRetournees);
        verify(coursRepository, times(1)).trouverParIdEleve(anyInt());
    }

    @Test
    public void pasDeCoursOuEleveEstInscrit()
    {
        // Arrange
        when(coursRepository.trouverParIdEleve(anyInt())).thenReturn(new ArrayList<Cours>());

        // Act
        ArrayList<Cours> listeCoursRetournee = serviceCours.getCoursInscrits(832);

        // Assert
        assertEquals(new ArrayList<Cours>(), listeCoursRetournee);
        assertEquals(0, listeCoursRetournee.size());
        verify(coursRepository, times(1)).trouverParIdEleve(anyInt());
    }
}
