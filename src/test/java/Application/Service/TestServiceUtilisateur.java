package Application.Service;

import Application.Services.ServiceUtilisateur;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class  TestServiceUtilisateur {

    @Mock
    private IUtilisateurRepository utilisateurRepository;

    @InjectMocks
    private ServiceUtilisateur serviceUtilisateur;

    @Test
    void getProfil_HappyPath() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser", "PrenomUser", "user@email.com", "hash");
        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);

        // Act
        Utilisateur resultat = serviceUtilisateur.getProfil(id);

        // Assert
        assertThat(resultat).isNotNull();
        assertThat(resultat.getEmail()).isEqualTo("user@email.com");
        verify(utilisateurRepository).trouverParId(id);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    void getProfil_Erreur_IdInvalide() {
        // Arrange
        int id = 0;

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.getProfil(id))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    void getProfil_Erreur_UtilisateurIntrouvable() {
        // Arrange
        int id = 99;
        when(utilisateurRepository.trouverParId(id)).thenReturn(null);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.getProfil(id))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    void modifierProfil_HappyPath_ModifiePseudoEtEmail() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser", "PrenomUser", "ancien@email.com", "hash");
        utilisateur.setPseudo("ancienPseudo");

        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);
        when(utilisateurRepository.mettreAJour(utilisateur)).thenReturn(1);

        // Act
        serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com");

        // Assert
        assertThat(utilisateur.getPseudo()).isEqualTo("nouveauPseudo");
        assertThat(utilisateur.getEmail()).isEqualTo("new@email.com");

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verify(utilisateurRepository).mettreAJour(utilisateur);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    void modifierProfil_Erreur_IdInvalide() {
        // Arrange
        int id = -5;

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "email@test.com"))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    void modifierProfil_Erreur_UtilisateurIntrouvable() {
        // Arrange
        int id = 10;
        when(utilisateurRepository.trouverParId(id)).thenReturn(null);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "email@test.com"))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    void modifierProfil_Erreur_EmailDejaUtilise() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser1", "PrenomUser1", "ancien@email.com", "hash");
        Utilisateur autreUtilisateur = new Utilisateur("NomUser2", "PrenomUser2", "nouveau@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);
        when(utilisateurRepository.trouverParEmail("nouveau@email.com")).thenReturn(autreUtilisateur);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "nouveau@email.com"))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("nouveau@email.com");
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    void modifierProfil_Erreur_MiseAJourEchouee() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser", "PrenomUser", "ancien@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);
        when(utilisateurRepository.trouverParEmail("nouveau@email.com")).thenReturn(null);
        when(utilisateurRepository.mettreAJour(utilisateur)).thenReturn(0);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, null, "nouveau@email.com"))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("nouveau@email.com");
        verify(utilisateurRepository).mettreAJour(utilisateur);
        verifyNoMoreInteractions(utilisateurRepository);
    }
}
