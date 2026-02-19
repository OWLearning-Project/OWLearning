package Infrastructure.Repository;

import app.OwLearning.Domain.Models.Createur;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUtilisateurRepository {
    @Mock
    private JpaUtilisateurRepository repositoryJpa;

    @InjectMocks
    private UtilisateurRepository repository;

    private Utilisateur utilisateur;
    private Createur createur;
    private Eleve eleve;

    @BeforeEach
    void setUp() {
        this.utilisateur = new Utilisateur("Dupont", "Bob", "bob@test.com", "mdpHash");
        this.createur = new Createur("Merveilles", "Alice", "alice@test.com", "mdpHash");
        this.eleve = new Eleve("Random", "Max", "max@test.com", "mdpHash");
    }

    @Test
    public void utilisateurBienInsereDansLaBd() {
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenReturn(this.utilisateur);

        // Act
        Utilisateur utilisateurInsere = repository.sauvegarder(this.utilisateur);

        // Assert
        assertEquals(this.utilisateur, utilisateurInsere);
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void utilisateurPasInsereDansLaBd() {
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenThrow(new RuntimeException("L'utilisateur n'a pas été inséré"));

        // Assert
        assertThrows(RuntimeException.class, () -> repository.sauvegarder(this.utilisateur));
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void emailTrouve() {
        // Arrange
        String email = "bob@test.com";

        when(repositoryJpa.findByEmail(any(String.class))).thenReturn(this.utilisateur);

        // Act
        Utilisateur utilisateurTrouve = repository.trouverParEmail(email);

        // Assert
        assertEquals(this.utilisateur, utilisateurTrouve);
        verify(repositoryJpa, times(1)).findByEmail(any(String.class));
    }

    @Test
    public void emailNonTrouve() {
        // Arrange
        String email = "emailInconnu@test.com";

        when(repositoryJpa.findByEmail(any(String.class))).thenReturn(null);

        // Act
        Utilisateur utilisateurTrouve = repository.trouverParEmail(email);

        // Assert
        assertNotEquals(this.utilisateur, utilisateurTrouve);
        verify(repositoryJpa, times(1)).findByEmail(any(String.class));
    }
    @Test
    public void trouverParId(){
        // Arrange
        int id = 1;
        when(repositoryJpa.findById(anyInt())).thenReturn(Optional.of(this.utilisateur));

        // Act
        Utilisateur utilisateurTrouve = repository.trouverParId(id);

        // Assert
        assertEquals(this.utilisateur, utilisateurTrouve);
        verify(repositoryJpa, times(1)).findById(anyInt());

    }

    @Test
    public void UtilisateurPasId(){
        //Arrange
        int id = 999;
        when(repositoryJpa.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Utilisateur resultat = repository.trouverParId(id);

        // Assert
        assertNull(resultat);
        verify(repositoryJpa, times(1)).findById(anyInt());
    }

    public void mettreAJour(){
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenReturn(this.utilisateur);

        // Act
        int lignes = repository.mettreAJour(utilisateur);

        // Assert
        assertEquals(1, lignes);
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void EchecMiseAJour(){
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenReturn(null);

        // Act
        int lignes = repository.mettreAJour(utilisateur);

        // Assert
        assertEquals(0, lignes);
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
        }
}