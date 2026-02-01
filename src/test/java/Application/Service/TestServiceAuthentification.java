package Application.Service;

import Application.Services.ServiceAuthentification;
import Domain.Models.Createur;
import Domain.Models.Eleve;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Domain.Ports.IServices.*;
import Shared.Exceptions.ExceptionCompteExistant;
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
    public void inscriptionCreateurValide()
    {
        // Arrange
        String email = "test@example.com";
        String mdp = "password123";
        String mdpHash = "hashed_password123";
        String prenom = "Bob";
        String nom = "Dylan";
        String role = "CREATEUR";

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(null);
        when(hacher.hacher(mdp)).thenReturn(mdpHash);
        when(utilisateurRepository.sauvegarder(any(Createur.class))).thenReturn(1);

        // Act & Assert
        assertTrue(serviceAuthentification.inscription(nom, prenom, email, mdp, 0, null, role));
        verify(utilisateurRepository).sauvegarder(any(Createur.class));
    }

    @Test
    public void createurDejaInscrit()
    {
        // Arrange
        String email = "test@example.com";
        String mdp = "password123";
        String mdpHash = "hashed_password123";
        String prenom = "Bob";
        String nom = "Dylan";
        String role = "createur";

        Utilisateur utilisateurSauvegarde = new Createur(nom, prenom, email, mdpHash);

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(utilisateurSauvegarde);

        // Act & Assert
        assertThrows(ExceptionCompteExistant.class, () -> serviceAuthentification.inscription(nom, prenom, email, mdp, 0, null, role));
    }

    @Test
    public void inscriptionEleveValide()
    {
        // Arrange
        String email = "test@example.com";
        String mdp = "password123";
        String mdpHash = "hashed_password123";
        String prenom = "Bob";
        String nom = "Dylan";
        String niveauEtude = "BAC +3";
        int age = 20;
        String role = "ELEVE";

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(null);
        when(hacher.hacher(mdp)).thenReturn(mdpHash);
        when(utilisateurRepository.sauvegarder(any(Eleve.class))).thenReturn(1);

        // Act & Assert
        assertTrue(serviceAuthentification.inscription(nom, prenom, email, mdp, age, niveauEtude, role));
        verify(utilisateurRepository).sauvegarder(any(Eleve.class));
    }

    @Test
    public void eleveDejaInscrit()
    {
        // Arrange
        String email = "test@example.com";
        String mdp = "password123";
        String mdpHash = "hashed_password123";
        String prenom = "Bob";
        String nom = "Dylan";
        String niveauEtude = "BAC +3";
        int age = 20;
        String role = "ELEVE";

        Utilisateur utilisateurSauvegarde = new Eleve(nom, prenom, email, mdpHash, age, niveauEtude);

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(utilisateurSauvegarde);

        // Act & Assert
        assertThrows(ExceptionCompteExistant.class, () -> serviceAuthentification.inscription(nom, prenom, email, mdp, 0, null, role));
    }

    @Test
    public void inscriptionAvecRoleInexistant()
    {
        // Arrange
        String email = "test@example.com";
        String mdp = "password123";
        String mdpHash = "hashed_password123";
        String prenom = "Bob";
        String nom = "Dylan";
        String role = "cr&ateur";

        when(utilisateurRepository.trouverParEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceAuthentification.inscription(nom, prenom, email, mdp, 0, null, role));
    }

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
