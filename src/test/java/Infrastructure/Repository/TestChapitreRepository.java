package Infrastructure.Repository;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;
import Domain.Models.TypeRessource;
import Infrastructure.Persistence.Interface.JpaChapitreRepository;
import Infrastructure.Persistence.Repository.ChapitreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
@ExtendWith(MockitoExtension.class)
public class TestChapitreRepository {
    private Chapitre chapitre;
    private Ressource ressource;
    @Mock
    private JpaChapitreRepository jpaRepository;
    @InjectMocks
    private ChapitreRepository chapitreRepository;
    @BeforeEach
    void setUp(){
        chapitre = new Chapitre("Chapitre1","c'est le chapitre 1", new ArrayList<>());
        ressource = new Ressource("PDF", TypeRessource.FICHIER_PDF,"http://dafzeffzefzefzfef");
    }

    @Test
    public void SauvegarderUnChapitre(){
        // Arrange
        when(jpaRepository.save(any(Chapitre.class))).thenReturn(chapitre);

        // Act
        int idRetourner = chapitreRepository.sauvegarder(chapitre);

        // Assert
        verify(jpaRepository, times(1)).save(chapitre);
        assertEquals("Les deux id sont egaux",chapitre.getId(), idRetourner);
    }

    @Test
    public void TrouverChapitreParIdExistant(){
        // Arrange
        int idChapitre = 11;
        when(jpaRepository.findById(idChapitre)).thenReturn(Optional.of(chapitre));

        // Act
        Chapitre resultat = chapitreRepository.trouverParId(idChapitre);

        // Assert
        assertNotNull(resultat);
        assertEquals("Comparaison des titres",chapitre.getTitre(), resultat.getTitre());
        verify(jpaRepository, times(1)).findById(idChapitre);
    }

    @Test
    public void TrouverChapitreParIdNonExistant(){
        // Arrange
        int idChapitre = 123;
        when(jpaRepository.findById(idChapitre)).thenReturn(Optional.empty());

        // Act
        Chapitre resultat = chapitreRepository.trouverParId(idChapitre);

        // Assert
        assertNull(resultat);
    }

    @Test
    public void SupprimerChapitreParIdExistant(){
        // Arrange
        int idChapitre = 11;
        when(jpaRepository.findById(idChapitre)).thenReturn(Optional.of(chapitre));

        // Act
        Chapitre resultat = chapitreRepository.supprimerParId(idChapitre);

        // Assert
        assertNotNull(resultat, "Le chapitre supprime doit etre retourne");
        assertEquals("Comparaison des titres", chapitre.getTitre(), resultat.getTitre());
        verify(jpaRepository, times(1)).delete(chapitre);
    }

    @Test
    public void SupprimerChapitreParIdNonExistant(){
        // Arrange
        int idChapitre = 999;
        when(jpaRepository.findById(idChapitre)).thenReturn(Optional.empty());

        // Act
        Chapitre resultat = chapitreRepository.supprimerParId(idChapitre);

        // Assert
        assertNull(resultat, "Doit retourner null si le chapitre n'existe pas");
        verify(jpaRepository, never()).delete(any());
    }




}
