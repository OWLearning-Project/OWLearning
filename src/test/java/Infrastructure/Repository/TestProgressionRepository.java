package Infrastructure.Repository;

import Domain.Models.Progression;
import Domain.Models.ProgressionId;
import Infrastructure.Persistence.Interface.JpaProgressionRepository;
import Infrastructure.Persistence.Repository.ProgressionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestProgressionRepository {

    @Mock
    private JpaProgressionRepository jpaProgressionRepository;

    @InjectMocks
    private ProgressionRepository progressionRepository;

    /**
     * Teste la récupération d'une progression grâce à la méthode trouverParId
     */
    @Test
    public void TestProgressionTrouvee(){
        //Arrange
        int elevId = 1;
        int coursId = 58;
        ProgressionId id = new ProgressionId(coursId, elevId);
        Progression progressionTest = mock(Progression.class);
        when(jpaProgressionRepository.findById(id)).thenReturn(Optional.of(progressionTest));

        //Act
        Progression resultat = progressionRepository.trouverParId(elevId, coursId);

        //Assert
        assertEquals(progressionTest, resultat);
        verify(jpaProgressionRepository, times(1)).findById(id);
    }

    /**
     * Teste si même avec une progression null on passe bien par le jpaProgression
     */
    @Test
    public void TestAucuneProgressionTrouvee(){
        //Arrange
        int elevId = 8;
        int coursId = 63;
        ProgressionId id = new ProgressionId(coursId, elevId);

        when(jpaProgressionRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        Progression resultat = progressionRepository.trouverParId(elevId, coursId);

        //Assert
        assertNull(resultat);
        verify(jpaProgressionRepository, times(1)).findById(id);
    }
}
