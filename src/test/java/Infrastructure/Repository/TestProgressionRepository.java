package Infrastructure.Repository;

import Domain.Models.Progression;
import Infrastructure.Persistence.Interface.JpaProgressionRepository;
import Infrastructure.Persistence.Repository.ProgressionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Progression progressionTest = mock(Progression.class);
        when(jpaProgressionRepository.findByEleveAndCoursNative(elevId, coursId)).thenReturn(progressionTest);

        //Act
        Progression resultat = progressionRepository.trouverParId(elevId, coursId);

        //Assert
        assertEquals(progressionTest, resultat);
        verify(jpaProgressionRepository, times(1)).findByEleveAndCoursNative(elevId, coursId);
    }

    /**
     * Teste si même avec une progression null on passe bien par le jpaProgression
     */
    @Test
    public void TestAucuneProgressionTrouvee(){
        //Arrange
        int elevId = 8;
        int coursId = 63;
        when(jpaProgressionRepository.findByEleveAndCoursNative(elevId, coursId)).thenReturn(null);

        //Act
        Progression resultat = progressionRepository.trouverParId(elevId, coursId);

        //Assert
        assertNull(resultat);
        verify(jpaProgressionRepository, times(1)).findByEleveAndCoursNative(elevId, coursId);
    }
}
