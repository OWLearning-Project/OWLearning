package Application.Service;

import app.OwLearning.Application.Services.ServiceChapitre;
import app.OwLearning.Domain.Ports.IRepository.IChapitreRepository;
import app.OwLearning.Shared.Exceptions.ExceptionChapitreIntrouvable;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import app.OwLearning.Domain.Models.*;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceChapitre {
    @Mock
    private IChapitreRepository repository;

    @InjectMocks
    private ServiceChapitre serviceChapitre;

    private Chapitre chapitre;
    private Ressource ressource;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        chapitre = new Chapitre("Chapitre Test", "Description Test", new ArrayList<>());
        //ReflectionTestUtils.setField(chapitre, "id", 1);

        ressource = new Ressource("PDF Cours", TypeRessource.FICHIER_PDF, "http://test.com");
        //ReflectionTestUtils.setField(ressource, "id_ressource", 50);
    }

    @Test
    public void GetContenuExiste() {
        if (chapitre == null){
            throw new RuntimeException("Erreur dans le test");
        }
        // Arrange
        int idChapitre = 1;
        when(repository.trouverParId(idChapitre)).thenReturn(chapitre);

        // Act
        Chapitre resultat = serviceChapitre.getContenuChapitre(idChapitre);

        // Assert
        assertNotNull(resultat);
        assertEquals("Chapitre Test", resultat.getTitre());
        verify(repository, times(1)).trouverParId(idChapitre);
    }

    @Test
    public void ModifierTitreEtDescription() {
        // ARRANGE
        int id = 1;
        String nouvTitre = "Nouveau Titre";
        String nouvDesc = "Nouvelle Description";

        when(repository.trouverParId(id)).thenReturn(chapitre);

        // ACT
        serviceChapitre.modifier(id, nouvTitre, nouvDesc);

        // ASSERT
        assertEquals(nouvTitre, chapitre.getTitre());
        assertEquals(nouvDesc, chapitre.getDescription());
        verify(repository).sauvegarder(chapitre);
    }

    @Test
    public void ModifierTitre() {
        // ARRANGE
        int id = 1;
        String nouvTitre = "Titre Seulement";

        when(repository.trouverParId(id)).thenReturn(chapitre);

        // ACT
        serviceChapitre.modifier(id, nouvTitre, null);

        // ASSERT
        assertEquals(nouvTitre, chapitre.getTitre(), "Le titre doit changer");
        assertEquals("Description Test", chapitre.getDescription(), "La description ne doit pas changer");
        verify(repository).sauvegarder(chapitre);
    }

    @Test
    public void ModifierDescription() {
        // ARRANGE
        int id = 1;
        String nouvDesc = "Description Seulement";

        when(repository.trouverParId(id)).thenReturn(chapitre);

        // ACT
        serviceChapitre.modifier(id, null, nouvDesc);

        // ASSERT
        assertEquals("Chapitre Test", chapitre.getTitre(), "Le titre ne doit pas changer");
        assertEquals(nouvDesc, chapitre.getDescription(), "La description doit changer");
        verify(repository).sauvegarder(chapitre);
    }

    @Test
    public void ModifierExceptionChapitreInexistant() {
        // ARRANGE
        when(repository.trouverParId(anyInt())).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionChapitreIntrouvable.class, () -> {
            serviceChapitre.modifier(99, "Titre", "Desc");
        });
        verify(repository, never()).sauvegarder(any());
    }

    @Test
    public void RetirerRessourceExceptionRessourceAbsente() {
        // ARRANGE
        when(repository.trouverParId(1)).thenReturn(chapitre);

        // ACT & ASSERT
        assertThrows(ExceptionRessourceIntrouvable.class, () -> { serviceChapitre.retirerRessource(1, 50);});
        verify(repository, never()).sauvegarder(any());
    }

    @Test
    public void RetirerRessourceExceptionChapitreInexistant() {
        // ARRANGE
        when(repository.trouverParId(99)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionChapitreIntrouvable.class, () -> {
            serviceChapitre.retirerRessource(99, 50);
        });
    }

    @Test
    public void AjouteUneRessourceAuChapitre(){
        // Arrange
        int idChapitre = 1;
        when(repository.trouverParId(idChapitre)).thenReturn(chapitre);
        when(repository.sauvegarder(any(Chapitre.class))).thenReturn(chapitre.getId());

        // Act
        serviceChapitre.ajouterRessource(idChapitre, ressource);

        // Assert
        verify(repository).trouverParId(idChapitre);
        AssertionErrors.assertEquals("Taille de la liste de ressource du chapitre",1, chapitre.getRessources().size());
        AssertionErrors.assertEquals("Nom de la ressource","PDF Cours", chapitre.getRessources().get(0).getNom());
        verify(repository).sauvegarder(chapitre);

    }

    @Test
    public void SupprimerUneRessourceAuChapitre(){
        // Arrange
        int idChapitre = 1;
        chapitre.ajouterRessource(ressource);
        when(repository.trouverParId(idChapitre)).thenReturn(chapitre);
        when(repository.sauvegarder(any(Chapitre.class))).thenReturn(idChapitre);

        // Act
        serviceChapitre.retirerRessource(idChapitre, ressource.getId_ressource());

        // Assert
        assertTrue(chapitre.getRessources().isEmpty(), "La liste devrait etre vide apres suppression");
        verify(repository).sauvegarder(chapitre);
    }

}