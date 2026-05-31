package Services;

import app.OwLearning.Services.Services.ServiceUtilisateur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void getProfil() {
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
    public void getProfilAvecIdInvalide() {
        // Arrange
        int id = 0;

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.getProfil(id))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void getProfilUtilisateurIntrouvable() {
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
    public void modifierProfilModifiePseudoEtEmail() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser", "PrenomUser", "ancien@email.com", "hash");
        utilisateur.setPseudo("ancienPseudo");

        Utilisateur utilisateurModifie = new Utilisateur("NomUser", "PrenomUser", "new@email.com", "hash");
        utilisateurModifie.setPseudo("nouveauPseudo");

        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);
        when(utilisateurRepository.sauvegarder(utilisateur)).thenReturn(utilisateurModifie);

        // Act
        Utilisateur resultat = serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com", null, null);

        // Assert
        assertThat(resultat.getPseudo()).isEqualTo("nouveauPseudo");
        assertThat(resultat.getEmail()).isEqualTo("new@email.com");

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verify(utilisateurRepository).sauvegarder(utilisateur);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilAvecIdInvalide() {
        // Arrange
        int id = -5;

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "email@test.com", 23, "Bac+3"))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilUtilisateurIntrouvable() {
        // Arrange
        int id = 10;
        when(utilisateurRepository.trouverParId(id)).thenReturn(null);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "email@test.com", 23, "Bac+3"))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilAvecEmailDejaUtilise() {
        // Arrange
        int id = 1;
        Utilisateur utilisateur = new Utilisateur("NomUser1", "PrenomUser1", "ancien@email.com", "hash");
        Utilisateur autreUtilisateur = new Utilisateur("NomUser2", "PrenomUser2", "nouveau@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(utilisateur);
        when(utilisateurRepository.trouverParEmail("nouveau@email.com")).thenReturn(autreUtilisateur);

        // Act + Assert
        assertThatThrownBy(() -> serviceUtilisateur.modifierProfil(id, "pseudo", "nouveau@email.com", 23, "Bac+3"))
                .isInstanceOf(IllegalStateException.class);

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("nouveau@email.com");
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilEleveAvecAgeEtNiveauEtude()
    {
        // Arrange
        int id = 1;
        Eleve eleve = new Eleve("NomUser", "PrenomUser", "ancien@email.com", "hash");

        Eleve eleveModifie = new Eleve("NomUser", "PrenomUser", "new@email.com", "hash");
        eleveModifie.setPseudo("nouveauPseudo");
        eleveModifie.setNiveauEtude("Bac+3");
        eleveModifie.setAge(23);


        when(utilisateurRepository.trouverParId(id)).thenReturn(eleve);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);
        when(utilisateurRepository.sauvegarder(eleve)).thenReturn(eleveModifie);

        // Act
        Utilisateur resultat = serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com", 23, "Bac+3");
        Eleve eleveResultat = (Eleve) resultat;

        // Assert
        assertThat(eleveResultat.getPseudo()).isEqualTo("nouveauPseudo");
        assertThat(eleveResultat.getEmail()).isEqualTo("new@email.com");
        assertThat(eleveResultat.getNiveauEtude()).isEqualTo("Bac+3");
        assertThat(eleveResultat.getAge()).isEqualTo(23);


        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verify(utilisateurRepository).sauvegarder(eleve);
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilEleveSansAge()
    {
        // Arrange
        int id = 1;
        Eleve eleve = new Eleve("NomUser", "PrenomUser", "ancien@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(eleve);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com", null, "Bac+3"));

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilEleveAgeInferieurA0()
    {
        // Arrange
        int id = 1;
        Eleve eleve = new Eleve("NomUser", "PrenomUser", "ancien@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(eleve);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com", -8, "Bac+3"));

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verifyNoMoreInteractions(utilisateurRepository);
    }

    @Test
    public void modifierProfilEleveSansNiveauEtude()
    {
        // Arrange
        int id = 1;
        Eleve eleve = new Eleve("NomUser", "PrenomUser", "ancien@email.com", "hash");

        when(utilisateurRepository.trouverParId(id)).thenReturn(eleve);
        when(utilisateurRepository.trouverParEmail("new@email.com")).thenReturn(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> serviceUtilisateur.modifierProfil(id, "nouveauPseudo", "new@email.com", 23, null));

        verify(utilisateurRepository).trouverParId(id);
        verify(utilisateurRepository).trouverParEmail("new@email.com");
        verifyNoMoreInteractions(utilisateurRepository);
    }
}
