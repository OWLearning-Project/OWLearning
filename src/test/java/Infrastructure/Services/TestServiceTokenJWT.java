package Infrastructure.Services;

import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Infrastructure.ServicesExternes.ServiceTokenJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceTokenJWT {

    private ServiceTokenJWT serviceToken;

    @Mock
    private Utilisateur utilisateurMock;

    @BeforeEach
    void setUp() {
        String cleTest = "MaCleDeTestTresLonguePourEtreValide123456";
        long expirationTest = 3600000L;

        serviceToken = new ServiceTokenJWT(cleTest, expirationTest);
    }

    @Test
    void genererTokenDoitRetournerUneChaineNonVide() {
        String token = serviceToken.genererToken(utilisateurMock);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void extraireIdDoitRetournerLeBonId() {
        when(utilisateurMock.getIdUtilisateur()).thenReturn(99);
        String token = serviceToken.genererToken(utilisateurMock);

        int idExtrait = serviceToken.extraireID(token);

        assertEquals(99, idExtrait);
    }

    @Test
    void validerTokenDoitRetournerVraiPourTokenValide() {
        String token = serviceToken.genererToken(utilisateurMock);

        assertTrue(serviceToken.validerToken(token));
    }

    @Test
    void validerTokenDoitRetournerFauxSiTokenModifie() {
        String token = serviceToken.genererToken(utilisateurMock);
        String tokenFaux = token + "a";

        assertFalse(serviceToken.validerToken(tokenFaux));
    }

    @Test
    void invaliderTokenDoitRendreLeTokenInvalide() {
        String token = serviceToken.genererToken(utilisateurMock);
        assertTrue(serviceToken.validerToken(token), "Le token doit être valide au début");

        serviceToken.invaliderToken(token);

        assertFalse(serviceToken.validerToken(token), "Le token ne doit plus être valide après invalidation");
    }

    @Test
    void invaliderTokenNeDoitPasPlanterSiTokenNull() {
        assertDoesNotThrow(() -> serviceToken.invaliderToken(null));
        assertDoesNotThrow(() -> serviceToken.invaliderToken(""));
    }
}
