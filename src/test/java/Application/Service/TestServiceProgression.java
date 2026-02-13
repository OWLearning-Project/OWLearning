package Application.Service;

import Application.Services.ServiceProgression;
import Domain.Models.Chapitre;
import Domain.Models.Cours;
import Domain.Models.Eleve;
import Domain.Models.Progression;
import Domain.Ports.IRepository.IProgressionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceProgression {

    @Mock
    private IProgressionRepository repositoryTest;

    @InjectMocks
    private ServiceProgression serviceTester;

    @Test
    public void TestRecuperationProgression(){
        //Arrange
        Eleve eleveTest = mock(Eleve.class);
        int eleveId = 1;
        Cours coursTest = mock(Cours.class);
        int coursId = 9;

        Progression progressionTest = new Progression(coursTest, eleveTest);
        progressionTest.setTauxProgression(75.0f);

        when(repositoryTest.trouverParId(eleveId, coursId)).thenReturn(progressionTest);

        //Act
        float resultat = serviceTester.getProgressionEleve(eleveId, coursId);

        //Assert
        assertEquals(75.0f, resultat);
    }
}
