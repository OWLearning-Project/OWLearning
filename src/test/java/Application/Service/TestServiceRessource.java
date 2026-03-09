package Application.Service;

import app.OwLearning.Application.Services.ServiceRessource;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Models.TypeRessource;
import app.OwLearning.Domain.Ports.IRepository.IRessourceRepository;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceRessource {

    @Mock
    private IRessourceRepository repository;

    @InjectMocks
    private ServiceRessource serviceRessource;

    private Ressource ressource;

    @BeforeEach
    void setUp() {
        ressource = new Ressource("Mon PDF", TypeRessource.FICHIER_PDF, "http://monpdf.com");
        ReflectionTestUtils.setField(ressource, "id_ressource", 10);
    }

    @Test
    public void GetContenu() {
        // ARRANGE
        when(repository.trouverParId(10)).thenReturn(ressource);

        // ACT
        Ressource resultat = serviceRessource.getContenuRessource(10);

        // ASSERT
        assertNotNull(resultat);
        assertEquals("Mon PDF", resultat.getNom());
        verify(repository).trouverParId(10);
    }

    @Test
    public void GetContenuException() {
        // ARRANGE
        when(repository.trouverParId(99)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionRessourceIntrouvable.class, () -> {
            serviceRessource.getContenuRessource(99);
        });
    }

    @Test
    public void CreeRessource() {
        // Arrange
        String nom = "Nouveau";
        String url = "http://nouveau.com";
        TypeRessource type = TypeRessource.VIDEO;

        Ressource ressourceCree = new Ressource(nom, type, url);
        when(repository.sauvegarder(any(Ressource.class))).thenReturn(ressourceCree);

        // Act
        Ressource resultat = serviceRessource.creeRessource(nom, url, type);

        // Assert
        assertThat(resultat).isSameAs(ressourceCree);
        verify(repository).sauvegarder(any(Ressource.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void CreeRessourceExceptionNomInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviceRessource.creeRessource("", "http://url.com", TypeRessource.FICHIER_PDF);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            serviceRessource.creeRessource(null, "http://url.com", TypeRessource.FICHIER_PDF);
        });
        verify(repository, never()).sauvegarder(any());
    }

    @Test
    public void CreeRessourceExceptionUrlInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviceRessource.creeRessource("Nom", "", TypeRessource.FICHIER_PDF);
        });
        verify(repository, never()).sauvegarder(any());
    }

    @Test
    public void CreeRessourceExceptionTypeInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviceRessource.creeRessource("Nom", "http://url.com", null);
        });
        verify(repository, never()).sauvegarder(any());
    }

    @Test
    public void SupprimerRessource_DoitSupprimer_SiExiste() {
        // ARRANGE
        when(repository.trouverParId(10)).thenReturn(ressource);

        // ACT
        Ressource resultat = serviceRessource.supprimerRessource(10);

        // ASSERT
        assertNotNull(resultat);
        assertEquals(10, resultat.getId_ressource());
        verify(repository).supprimer(10);
    }

    @Test
    public void SupprimerRessourceException() {
        // ARRANGE
        when(repository.trouverParId(99)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionRessourceIntrouvable.class, () -> {
            serviceRessource.supprimerRessource(99);
        });
        verify(repository, never()).supprimer(anyInt());
    }

    @Test
    public void Modifier_DoitToutMettreAJour_SiDonneesValides() {
        // ARRANGE
        when(repository.trouverParId(10)).thenReturn(ressource);

        // ACT
        serviceRessource.modifier(10, "NouvNom", "http://nouv.com", TypeRessource.FICHIER_PDF);

        // ASSERT
        assertEquals("NouvNom", ressource.getNom());
        assertEquals("http://nouv.com", ressource.getUrl());
        assertEquals(TypeRessource.FICHIER_PDF, ressource.getType());
        verify(repository).sauvegarder(ressource);
    }

    @Test
    public void Modifier_NeDoitPasMettreAJour_LesChampsVidesOuNulls() {
        // ARRANGE
        when(repository.trouverParId(10)).thenReturn(ressource);

        // ACT
        serviceRessource.modifier(10, "", null, TypeRessource.VIDEO);

        // ASSERT
        assertEquals("Mon PDF", ressource.getNom(), "Le nom ne doit pas avoir changé");
        assertEquals("http://monpdf.com", ressource.getUrl(), "L'url ne doit pas avoir changé");
        assertEquals(TypeRessource.VIDEO, ressource.getType(), "Le type DOIT avoir changé");
        verify(repository).sauvegarder(ressource);
    }

    @Test
    public void Modifier_DoitLeverException_SiNexistePas() {
        // ARRANGE
        when(repository.trouverParId(99)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionRessourceIntrouvable.class, () -> {
            serviceRessource.modifier(99, "Nom", "Url", TypeRessource.FICHIER_PDF);
        });

        verify(repository, never()).sauvegarder(any());
    }
}