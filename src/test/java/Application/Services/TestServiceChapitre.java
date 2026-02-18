package Application.Services;

import Domain.Ports.IRepository.IChapitreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import Domain.Models.*;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;
import java.util.Optional;

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
    public void GetContenu_DoitRetournerChapitre_QuandIlExiste() {
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
