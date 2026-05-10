package Domain.TestModel;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDiscussion {

    @Test
    public void testLeMessageEstAjoute() throws ExceptionUtilisateurNonAutorise {
        Utilisateur fauxUtilisateur1 = new Utilisateur();
        Utilisateur fauxUtilisateur2 = new Utilisateur();
        Discussion fausseDiscussion = new Discussion(fauxUtilisateur1, fauxUtilisateur2);
        Message fauxMessage = new Message("Faux contenu", fauxUtilisateur1);

        fausseDiscussion.ajouterMessage(fauxMessage);

        assertTrue(fausseDiscussion.getMessages().contains(fauxMessage));
    }

    @Test
    public void testAjouteMessageNull() {
        Utilisateur fauxUtilisateur1 = new Utilisateur();
        Utilisateur fauxUtilisateur2 = new Utilisateur();
        Discussion fausseDiscussion = new Discussion(fauxUtilisateur1, fauxUtilisateur2);

        assertThrows(IllegalArgumentException.class, () -> fausseDiscussion.ajouterMessage(null));
    }

    @Test
    public void testUtilisateurNeFaitPasPartiDiscussion() {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);
        Utilisateur intrus = mock(Utilisateur.class);

        when(destinataire.getIdUtilisateur()).thenReturn(0);
        when(expediteur.getIdUtilisateur()).thenReturn(1);
        when(intrus.getIdUtilisateur()).thenReturn(2);

        Discussion discussion = new Discussion(expediteur, destinataire);
        assertFalse(discussion.utilisateurFaitParti(intrus.getIdUtilisateur()));
    }

    @Test
    public void testUtilisateurFaitPartiDiscussion() {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);

        when(destinataire.getIdUtilisateur()).thenReturn(0);
        when(expediteur.getIdUtilisateur()).thenReturn(1);

        Discussion discussion = new Discussion(destinataire, expediteur);
        assertTrue(discussion.utilisateurFaitParti(destinataire.getIdUtilisateur()));
        assertTrue(discussion.utilisateurFaitParti(expediteur.getIdUtilisateur()));
    }

    @Test
    public void testAjouterMessageEtUtilisateurFaisParti() throws ExceptionUtilisateurNonAutorise {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);

        when(destinataire.getIdUtilisateur()).thenReturn(0);
        when(expediteur.getIdUtilisateur()).thenReturn(1);

        Discussion discussion = new Discussion(expediteur, destinataire);

        Message fauxMessage = mock(Message.class);
        when(fauxMessage.getUtilisateur()).thenReturn(expediteur);

        discussion.ajouterMessage(fauxMessage);

        assertTrue(discussion.getMessages().contains(fauxMessage));
    }

    @Test
    public void testAjouterMessageEtUtilisateurFaitPasParti() {
        Utilisateur destinataire = mock(Utilisateur.class);
        Utilisateur expediteur = mock(Utilisateur.class);
        Utilisateur intrus = mock(Utilisateur.class);

        when(destinataire.getIdUtilisateur()).thenReturn(0);
        when(expediteur.getIdUtilisateur()).thenReturn(1);
        when(intrus.getIdUtilisateur()).thenReturn(2);

        Discussion discussion = new Discussion(destinataire, expediteur);

        Message fauxMessage = mock(Message.class);
        when(fauxMessage.getUtilisateur()).thenReturn(intrus);

        assertThrows(ExceptionUtilisateurNonAutorise.class, () -> discussion.ajouterMessage(fauxMessage));
    }
}
