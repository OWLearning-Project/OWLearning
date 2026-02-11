package Infrastructure.Repository;

import Domain.Models.*;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import Infrastructure.Persistence.Repository.UtilisateurRepository;
import Shared.Exceptions.ExceptionUtilisateurInexistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.de.siegmar.fastcsv.util.Util;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

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

}