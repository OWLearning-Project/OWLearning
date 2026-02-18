package Infrastructure.Repository;

import Domain.Models.Utilisateur;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import Infrastructure.Persistence.Repository.UtilisateurRepository;
import Shared.Exceptions.ExceptionUtilisateurInexistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    public void createurBienInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertCreateurNative(anyInt())).thenReturn(1);

        // Act
        int ligneInseree = repository.sauvegarderCreateur(23);

        // Assert
        assertEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertCreateurNative(anyInt());
    }

    @Test
    public void createurPasInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertCreateurNative(anyInt())).thenReturn(0);

        // Act
        int ligneInseree = repository.sauvegarderCreateur(193);

        // Assert
        assertNotEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertCreateurNative(anyInt());
    }

    @Test
    public void eleveBienInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertEleveNative(anyInt())).thenReturn(1);

        // Act
        int ligneInseree = repository.sauvegarderEleve(23);

        // Assert
        assertEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertEleveNative(anyInt());
    }

    @Test
    public void elevePasInsereDansLaBd()
    {
        // Arrange
        when(repositoryJpa.insertEleveNative(anyInt())).thenReturn(0);

        // Act
        int ligneInseree = repository.sauvegarderEleve(12);

        // Assert
        assertNotEquals(1, ligneInseree);
        verify(repositoryJpa, times(1)).insertEleveNative(anyInt());
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

    @Test
    public void idTrouveParEmail()
    {
        // Arrange
        int idAttendu = 12;
        String email = "email@test.com";

        when(repositoryJpa.findIdByEmailNative(any(String.class))).thenReturn(new Integer(12));

        // Act
        int idRecu = repository.trouverIdParEmail(email);

        // Assert
        assertEquals(idAttendu, idRecu);
        verify(repositoryJpa, times(1)).findIdByEmailNative(any(String.class));
    }

    @Test
    public void idNonTrouveParEmail()
    {
        // Arrange
        String email = "email@test.com";

        when(repositoryJpa.findIdByEmailNative(any(String.class))).thenReturn(null);

        // Act & Assert
        assertThrows(ExceptionUtilisateurInexistant.class, () -> repository.trouverIdParEmail(email));
        verify(repositoryJpa, times(1)).findIdByEmailNative(any(String.class));
    }

}
