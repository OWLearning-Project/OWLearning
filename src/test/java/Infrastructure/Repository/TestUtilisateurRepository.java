package Infrastructure.Repository;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;
import Domain.Models.Utilisateur;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import Infrastructure.Persistence.Repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.de.siegmar.fastcsv.util.Util;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUtilisateurRepository
{
    @Mock
    private JpaUtilisateurRepository repositoryJpa;

    @InjectMocks
    private UtilisateurRepository repository;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp()
    {
        this.utilisateur = new Utilisateur("Dupont", "Bob", "bob@test.com", "mdpHash");
    }

    @Test
    public void utilisateurBienInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertUtilisateurNative(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(1);

        // Act
        int ligneInseree = repository.sauvegarder(this.utilisateur);

        // Assert
        assertEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertUtilisateurNative(any(String.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void utilisateurPasInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertUtilisateurNative(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(0);

        // Act
        int ligneInseree = repository.sauvegarder(this.utilisateur);

        // Assert
        assertNotEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertUtilisateurNative(any(String.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void emailTrouve()
    {
        // Arrange
        String email = "bob@test.com";

        when(repositoryJpa.findByEmailNative(any(String.class))).thenReturn(this.utilisateur);

        // Act
        Utilisateur utilisateurTrouve = repository.trouverParEmail(email);

        // Assert
        assertEquals(this.utilisateur, utilisateurTrouve);
        verify(repositoryJpa, times(1)).findByEmailNative(any(String.class));
    }

    @Test
    public void emailNonTrouve()
    {
        // Arrange
        String email = "emailInconnu@test.com";

        when(repositoryJpa.findByEmailNative(any(String.class))).thenReturn(null);

        // Act
        Utilisateur utilisateurTrouve = repository.trouverParEmail(email);

        // Assert
        assertNotEquals(this.utilisateur, utilisateurTrouve);
        verify(repositoryJpa, times(1)).findByEmailNative(any(String.class));
    }

}
