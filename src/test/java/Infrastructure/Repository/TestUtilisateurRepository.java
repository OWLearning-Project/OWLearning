package Infrastructure.Repository;

import app.OwLearning.Domain.Models.Createur;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.UtilisateurRepository;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurInexistant;
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
    public void doitRetournerUtilisateurInsereDansLaBd() {
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenReturn(this.utilisateur);

        // Act
        Utilisateur utilisateurInsere = repository.sauvegarder(this.utilisateur);

        // Assert
        assertEquals(this.utilisateur, utilisateurInsere);
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void doitLancerUneExceptionCarUtilisateurNonInsereDansLaBd() {
        // Arrange
        when(repositoryJpa.save(any(Utilisateur.class))).thenThrow(new RuntimeException("L'utilisateur n'a pas été inséré"));

        // Assert
        assertThrows(RuntimeException.class, () -> repository.sauvegarder(this.utilisateur));
        verify(repositoryJpa, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void doitRetourneUtilisateurTrouveParEmail() {
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
    public void doitRetournerNullCarPasDUtilisateurAssocieAEmail() {
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
    public void doitRetournerUtilisateurTrouveParId()
    {
        // Arrange
        int idUtilisateur = 39;

        when(repositoryJpa.findById(idUtilisateur)).thenReturn(Optional.of(this.utilisateur));

        // Act
        Utilisateur utilisateurRecupere = repository.trouverParId(idUtilisateur);

        // Assert
        assertEquals(this.utilisateur, utilisateurRecupere);
        assertNotNull(utilisateurRecupere);
        verify(repositoryJpa, times(1)).findById(idUtilisateur);
    }

    @Test
    public void doitLancerUneExceptionCarPasDUtilisateurAssocieAId()
    {
        // Arrange
        int idUtilisateur = 10;

        when(repositoryJpa.findById(idUtilisateur)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExceptionUtilisateurInexistant.class, () -> repository.trouverParId(idUtilisateur));
        verify(repositoryJpa, times(1)).findById(idUtilisateur);
    }
}