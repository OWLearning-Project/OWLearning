package Infrastructure.Repository;

import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Models.TypeRessource;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaRessourceRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.RessourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRessourceRepository
{
    @Mock
    private JpaRessourceRepository jpaRessourceRepository;
    @InjectMocks
    private RessourceRepository ressourceRepository;
    private Ressource ressourceNouvelle;

    @BeforeEach
    void setup()
    {
        ressourceNouvelle = new Ressource("Image.png", TypeRessource.IMAGE,"http://url.com/image.png");
    }

    @Test
    public void sauvegarderNouvelleRessourceDoitAppelerSaveJPA()
    {
        // ARRANGE
        when(jpaRessourceRepository.save(ressourceNouvelle)).thenReturn(ressourceNouvelle);

        // ACT
        Ressource resultat = ressourceRepository.sauvegarder(ressourceNouvelle);

        // ASSERT
        verify(jpaRessourceRepository).save(ressourceNouvelle);
        assertEquals(ressourceNouvelle, resultat);
    }

    @Test
    public void trouverParIdDoitRetournerRessource()
    {
        // ARRANGE
        when(jpaRessourceRepository.findById(10)).thenReturn(Optional.of(ressourceNouvelle));

        // ACT
        Ressource resultat = ressourceRepository.trouverParId(10);

        // ASSERT
        assertNotNull(resultat);
        assertEquals(ressourceNouvelle, resultat);
    }

    @Test
    public void supprimerDoitAppelerDeleteById() {
        // ACT
        ressourceRepository.supprimer(10);

        // ASSERT
        verify(jpaRessourceRepository).deleteById(10);
    }
}
