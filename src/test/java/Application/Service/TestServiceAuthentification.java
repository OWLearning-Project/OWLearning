package Application.Service;

import Domain.Models.Utilisateur;
import Application.Services.ServiceAuthentification;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Domain.Ports.IServices.*;
import Shared.Exceptions.ExceptionMauvaisIdentifiants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
public class TestServiceAuthentification
{
    @Mock
    private IUtilisateurRepository utilisateurRepository;
    @Mock
    private IHach hacher;
    @Mock
    private IServiceToken serviceToken;

    @InjectMocks
    private ServiceAuthentification serviceAuthentification;


    @Test
    public void connexionDoitRenvoyerTokenEmailEtMdpValide()
    {
        // ARRANGE
        String email = "test@example.com";
        String mdpBrut = "password123";
        String mdpHash = "hashed_password123";
        String tokenAttendu = "fake-jwt-token";

        Utilisateur userMock = new Utilisateur("Test", "Utilisateur", email, mdpHash);
        userMock.setDateInscription(new Timestamp(System.currentTimeMillis()));

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(userMock);
        when(hacher.valider(mdpBrut, mdpHash)).thenReturn(true);
        when(serviceToken.genererToken(userMock)).thenReturn(tokenAttendu);

        // ACT
        String resultatToken = serviceAuthentification.connexion(email, mdpBrut);

        // ASSERT
        assertEquals(tokenAttendu, resultatToken);
        verify(utilisateurRepository).sauvegarder(userMock);
    }
    @Test
    void connexionDoitEchouerQuandUtilisateurEstNull()
    {
        // ARRANGE
        String email = "inconnu@example.com";

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(null);

        // ACT & ASSERT
        ExceptionMauvaisIdentifiants ex = assertThrows(ExceptionMauvaisIdentifiants.class, () -> {
            serviceAuthentification.connexion(email, "mdp");
        });

        assertTrue(ex.toString().contains(email));
    }

    @Test
    void connexionDoitEchouerQuandMotDePasseIncorrect()
    {
        // ARRANGE
        String email = "test@example.com";
        Utilisateur userMock = new Utilisateur("Nom", "Prenom", email, "vraiHash");

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(userMock);
        when(hacher.valider("mauvaisMdp", "vraiHash")).thenReturn(false);

        // ACT & ASSERT
        assertThrows(ExceptionMauvaisIdentifiants.class, () -> {
            serviceAuthentification.connexion(email, "mauvaisMdp");
        });
    }

    @Test
    void deconnexionDoitInvaliderLeTokenEtRenvoyerVrai() {
        // ARRANGE
        String token = "TOKEN_A_INVALIDER";

        // ACT
        boolean resultat = serviceAuthentification.deconnexion(token);

        // ASSERT
        assertTrue(resultat, "La déconnexion doit renvoyer true");
        verify(serviceToken, times(1)).invaliderToken(token);
    }

}
