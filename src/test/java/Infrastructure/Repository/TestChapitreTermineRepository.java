package Infrastructure.Repository;

import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.ChapitreTermine;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaChapitreTermineRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.ChapitreTermineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestChapitreTermineRepository
{
    @Mock
    private JpaChapitreTermineRepository jpaRepository;

    @InjectMocks
    private ChapitreTermineRepository chapitreTermineRepository;

    private Chapitre chapitre;
    private ChapitreTermine chapitreTermine;
    private final int idChapitre = 1;
    private final int idEleve = 11;

    @BeforeEach
    void setup()
    {
        chapitre = new Chapitre("Test", "test description", new ArrayList<>());
        chapitreTermine = new ChapitreTermine(chapitre, idEleve);
    }

    @Test
    void sauvegarderChapitreCommeTermine()
    {
        // Arrange
        when(jpaRepository.save(any(ChapitreTermine.class))).thenReturn(chapitreTermine);

        // Act
        chapitreTermineRepository.sauvegarder(chapitreTermine);

        // Assert
        verify(jpaRepository,times(1)).save(chapitreTermine);
    }
    @Test
    void existeRetourneTrueSiLigneTrouvee() {
        // Arrange
        when(jpaRepository.existsByChapitreTermineIdIdChapitreAndIdEleve(idChapitre, idEleve)).thenReturn(true);

        // Act
        boolean resultat = chapitreTermineRepository.existe(idChapitre, idEleve);

        // Assert
        assertTrue(resultat);
        verify(jpaRepository, times(1)).existsByChapitreTermineIdIdChapitreAndIdEleve(idChapitre, idEleve);
    }

    @Test
    void existeRetourneFalseSiLigneAbsente() {
        // Arrange
        when(jpaRepository.existsByChapitreTermineIdIdChapitreAndIdEleve(idChapitre, idEleve)).thenReturn(false);

        // Act
        boolean resultat = chapitreTermineRepository.existe(idChapitre, idEleve);

        // Assert
        assertFalse(resultat);
    }
}
