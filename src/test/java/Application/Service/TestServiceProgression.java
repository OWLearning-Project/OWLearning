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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceProgression {

    @Mock
    private IProgressionRepository repositoryTest;

    @InjectMocks
    private ServiceProgression serviceTester;

    @Test
    public void TestCoursTermines(){
        //Arrange
        Eleve eleveTest = mock(Eleve.class);
        when(eleveTest.getId()).thenReturn(1);

        Cours coursFini = mock(Cours.class);
        Progression progressionFini = new Progression(coursFini, eleveTest);
        progressionFini.setTauxProgression(100.0f);
        when(coursFini.getId()).thenReturn(1);
        when(coursFini.getTitre()).thenReturn(("Qualité de développement"));

        Cours coursPasFini = mock(Cours.class);
        Progression progressionPasFini = new Progression(coursPasFini, eleveTest);
        progressionPasFini.setTauxProgression(50.0f);
//        Pour valider le test nous avons pas besoin pour prouver que le filtrage est correct car dès lors que la progression est inférieur a 100 il passe a la suite
//        when(coursPasFini.getId()).thenReturn(2);
//        when(coursPasFini.getTitre()).thenReturn("Base de données");

        ArrayList<Progression> toutesLesProgressions = new ArrayList<>();
        toutesLesProgressions.add(progressionFini);
        toutesLesProgressions.add(progressionPasFini);

        when(repositoryTest.recupererToutesLesProgressionsParEleve(1)).thenReturn(toutesLesProgressions);

        //Act
        ArrayList<Cours> resultat = serviceTester.getCoursTermines(1);

        //Assert
        assertEquals(1, resultat.size());
        assertEquals("Qualité de développement", resultat.get(0).getTitre());
        assertEquals(1, resultat.get(0).getId());

    }

    @Test
    public void TestCoursPasTermines(){
        //Arrange
        Eleve eleveTest = mock(Eleve.class);
        when(eleveTest.getId()).thenReturn(1);

        Cours coursFini = mock(Cours.class);
        Progression progressionFini = new Progression(coursFini, eleveTest);
        progressionFini.setTauxProgression(100.0f);
//        when(coursFini.getId()).thenReturn(1);
//        when(coursFini.getTitre()).thenReturn(("Qualité de développement"));

        Cours coursPasFini1 = mock(Cours.class);
        Progression progressionPasFini1 = new Progression(coursPasFini1, eleveTest);
        progressionPasFini1.setTauxProgression(50.0f);
        when(coursPasFini1.getId()).thenReturn(2);
        when(coursPasFini1.getTitre()).thenReturn("Base de données");

        Cours coursPasFini2 = mock(Cours.class);
        Progression progressionPasFini2 = new Progression(coursPasFini2, eleveTest);
        progressionPasFini2.setTauxProgression(99.9f);
        when(coursPasFini2.getId()).thenReturn(3);
        when(coursPasFini2.getTitre()).thenReturn("Anglais");

        ArrayList<Progression> toutesLesProgressions = new ArrayList<>();
        toutesLesProgressions.add(progressionFini);
        toutesLesProgressions.add(progressionPasFini1);
        toutesLesProgressions.add(progressionPasFini2);

        when(repositoryTest.recupererToutesLesProgressionsParEleve(1)).thenReturn(toutesLesProgressions);

        //Act
        ArrayList<Cours> resultat = serviceTester.getCoursPasTermines(1);

        //Assert
        assertEquals(2, resultat.size());
        assertEquals("Base de données", resultat.get(0).getTitre());
        assertEquals(2, resultat.get(0).getId());
        assertEquals("Anglais", resultat.get(1).getTitre());
        assertEquals(3, resultat.get(1).getId());
    }

    @Test
    public void TestMajProgression(){
        //Arrange
        int eleveId = 1;
        int coursId = 10;
        int chapitreId = 2;

        Eleve eleveTest = mock(Eleve.class);
        Cours coursTest = mock(Cours.class);

        ArrayList<Chapitre> listeChapitres = new ArrayList<>();
        listeChapitres.add(mock(Chapitre.class));
        listeChapitres.add(mock(Chapitre.class));
        listeChapitres.add(mock(Chapitre.class));
        listeChapitres.add(mock(Chapitre.class));
        when(coursTest.getChapitres()).thenReturn(listeChapitres);

        Progression progressionExistante = new Progression(coursTest, eleveTest);
        progressionExistante.setTauxProgression(25.0f);

        when(repositoryTest.trouverParId(eleveId, coursId)).thenReturn(progressionExistante);

        //Act
        serviceTester.majProgression(eleveId, coursId, chapitreId);

        //Assert
        assertEquals(50.0f, progressionExistante.getTauxProgression());
        verify(repositoryTest).maj(progressionExistante);
    }

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
