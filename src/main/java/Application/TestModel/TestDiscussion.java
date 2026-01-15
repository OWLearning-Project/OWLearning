package Application.TestModel;

import Domain.Models.*;
import Shared.Exceptions.ExceptionsDiscussion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestDiscussion
{
    @Test
    public void testLeMessageEstAjoute()
    {
        //Arrange
        Utilisateur fauxUtilisateur1 = new Utilisateur();
        Utilisateur fauxUtilisateur2 = new Utilisateur();
        Discussion fausseDiscussion = new Discussion(fauxUtilisateur1, fauxUtilisateur2);
        Message fauxMessage = new Message("Faux contenu", fauxUtilisateur1,fausseDiscussion);

        //Act
        fausseDiscussion.ajouterMessage(fauxMessage);

        //Assert
        assertTrue(fausseDiscussion.getMessages().contains(fauxMessage));
    }

    @Test
    public void testAjouteMessageNull()
    {

        Utilisateur fauxUtilisateur1 = new Utilisateur();
        Utilisateur fauxUtilisateur2 = new Utilisateur();
        Discussion fausseDiscussion = new Discussion(fauxUtilisateur1, fauxUtilisateur2);

        assertThrows(ExceptionsDiscussion.class, ()->fausseDiscussion.ajouterMessage(null));
    }

    @Test
    public void testUtilisateurNeFaitPasPartiDiscussion()
    {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);
        Utilisateur intrus =  mock(Utilisateur.class);

        when(destinataire.getId()).thenReturn(0);
        when(expediteur.getId()).thenReturn(1);
        when(intrus.getId()).thenReturn(2);

        Discussion discussion = new Discussion(expediteur, destinataire);
        assertFalse(discussion.utilisateurFaitParti(intrus.getId()));
    }

    @Test
    public void testUtilisateurFaitPartiDiscussion()
    {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);

        when(destinataire.getId()).thenReturn(0);
        when(expediteur.getId()).thenReturn(1);

        Discussion discussion = new Discussion(destinataire, expediteur);
        assertTrue(discussion.utilisateurFaitParti(destinataire.getId()));
        assertTrue(discussion.utilisateurFaitParti(expediteur.getId()));
    }

    @Test
    public void testAjouterMessageEtUtilisateurFaisParti()
    {
        //Arrange
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);

        when(destinataire.getId()).thenReturn(0);
        when(expediteur.getId()).thenReturn(1);

        Discussion discussion = new Discussion(expediteur, destinataire);

        Message fauxMessage = mock(Message.class);
        when(fauxMessage.getUtilisateur()).thenReturn(expediteur);

        //Act
        discussion.ajouterMessage(fauxMessage);

        //Assert
        assertTrue(discussion.getMessages().contains(fauxMessage));
    }

    @Test
    public void testAjouterMessageEtUtilisateurFaitPasParti()
    {
        //Arrange
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);
        Utilisateur intrus = mock(Utilisateur.class);

        when(destinataire.getId()).thenReturn(0);
        when(expediteur.getId()).thenReturn(1);
        when(intrus.getId()).thenReturn(2);

        Discussion discussion = new Discussion(destinataire, expediteur);

        Message fauxMessage = mock(Message.class);
        when(fauxMessage.getUtilisateur()).thenReturn(intrus);

        //Assert
        assertThrows(ExceptionsDiscussion.class, ()->discussion.ajouterMessage(fauxMessage));
    }
}
