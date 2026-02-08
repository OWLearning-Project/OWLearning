package Infrastructure.Repository;

import Domain.Models.Ressource;
import Domain.Models.TypeRessource;
import Infrastructure.Persistence.Interface.JpaRessourceRepository;
import Infrastructure.Persistence.Repository.RessourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void sauvegarderNouvelleRessourceDoitInsererEtRecupererObjet()
    {
        // ARRANGE
        Ressource ressourceMock = mock(Ressource.class);
        when(jpaRessourceRepository.trouverParUrlNative(ressourceNouvelle.getUrl())).thenReturn(ressourceMock);

        // ACT
        Ressource resultat = ressourceRepository.sauvegarder(ressourceNouvelle);

        // ASSERT
        verify(jpaRessourceRepository).ajouterRessourceNative(eq(ressourceNouvelle.getNom()), eq(ressourceNouvelle.getUrl()), eq(ressourceNouvelle.getType().getLabel()));
        verify(jpaRessourceRepository).trouverParUrlNative(ressourceNouvelle.getUrl());
        assertEquals(ressourceMock, resultat);
    }

    @Test
    public void sauvegarderErreurInsertionDoitLeverException()
    {
        // ARRANGE
        when(jpaRessourceRepository.trouverParUrlNative(anyString())).thenReturn(null);

        // ACT & ASSERT
        assertThrows(RuntimeException.class,() -> ressourceRepository.sauvegarder(ressourceNouvelle));
    }

    @Test
    public void trouverParIdDoitRetournerRessource() {
        // ARRANGE
        Ressource ressourceMock = mock(Ressource.class);
        when(jpaRessourceRepository.trouverParIdNative(10)).thenReturn(ressourceMock);

        // ACT
        Ressource resultat = ressourceRepository.trouverParId(10);

        // ASSERT
        assertEquals(ressourceMock, resultat);
    }

}
