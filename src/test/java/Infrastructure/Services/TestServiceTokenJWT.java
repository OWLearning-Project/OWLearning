package Infrastructure.Services;

import Domain.Models.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceTokenJWT
{

    private ServiceTokenJWT serviceToken;
    @Mock
    private Utilisateur utilisateurMock;

    @BeforeEach
    void setUp()
    {
        // ARRANGE
        String cleTest = "MaCleDeTestTresLonguePourEtreValide123456";
        long expirationTest = 3600000L;

        serviceToken = new ServiceTokenJWT(cleTest, expirationTest);
    }

    @Test
    void genererTokenDoitRetournerUneChaineNonVide()
    {
        String token = serviceToken.genererToken(utilisateurMock);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void extraireIdDoitRetournerLeBonId()
    {
        // ARRANGE
        when(utilisateurMock.getId()).thenReturn(99);
        String token = serviceToken.genererToken(utilisateurMock);

        // ACT
        int idExtrait = serviceToken.extraireID(token);

        // ASSERT
        assertEquals(99, idExtrait);
    }

    @Test
    void validerTokenDoitRetournerVraiPourTokenValide()
    {
        String token = serviceToken.genererToken(utilisateurMock);
        assertTrue(serviceToken.validerToken(token));
    }

    @Test
    void validerTokenDoitRetournerFauxSiTokenModifie()
    {
        String token = serviceToken.genererToken(utilisateurMock);

        String tokenFaux = token + "a";

        assertFalse(serviceToken.validerToken(tokenFaux));
    }

    @Test
    void invaliderTokenDoitRendreLeTokenInvalide()
    {
        // ARRANGE
        String token = serviceToken.genererToken(utilisateurMock);
        assertTrue(serviceToken.validerToken(token), "Le token doit être valide au début");

        // ACT
        serviceToken.invaliderToken(token);

        // ASSERT
        assertFalse(serviceToken.validerToken(token), "Le token ne doit plus être valide après invalidation");
    }

    @Test
    void invaliderTokenNeDoitPasPlanterSiTokenNull()
    {
        assertDoesNotThrow(() -> serviceToken.invaliderToken(null));
        assertDoesNotThrow(() -> serviceToken.invaliderToken(""));
    }
}