package Application.Service;

import Application.Services.ServiceMessage;
import Domain.Models.Message;
import Domain.Models.Ressource;
import Domain.Models.TypeRessource;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IMessageRepository;
import Domain.Ports.IRepository.IRessourceRepository;
import Shared.Exceptions.ExceptionMessageIntrouvable;
import Shared.Exceptions.ExceptionRessourceIntrouvable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceMessage
{
    @Mock
    private IMessageRepository messageRepository;
    @Mock
    private IRessourceRepository ressourceRepository;
    @InjectMocks
    private ServiceMessage serviceMessage;

    @Test
    public void trouverMessageParDiscussionDoitRetournerListeMessages()
    {
        // ARRANGE
        int idDiscussion = 1;
        Message m1 = new Message("Bonjour", new Utilisateur());
        Message m2 = new Message("ça va ?", new Utilisateur());
        ArrayList<Message> messages = new ArrayList<>(Arrays.asList(m1, m2));
        when(messageRepository.trouverParDiscussion(idDiscussion)).thenReturn(messages);

        // ACT
        List<Message> resultat = serviceMessage.trouverMessageParDiscussion(idDiscussion);

        // ASSERT
        assertNotNull(resultat);
        assertEquals(2,resultat.size());
        verify(messageRepository, times(1)).trouverParDiscussion(idDiscussion);
    }

    @Test
    public void trouverMessageParDiscussionSiAucunMessageRetournerListeMessagesVide()
    {
        // ARRANGE
        int idDiscussion = 1;
        when(messageRepository.trouverParDiscussion(idDiscussion)).thenReturn(new ArrayList<>());

        // ACT
        List<Message> resultat = serviceMessage.trouverMessageParDiscussion(idDiscussion);

        // ASSERT
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
    }

    @Test
    public void ajouterRessourceDoitAjouterEtSauvegarder()
    {
        // ARRANGE
        int idMessage = 1;
        int idRessource = 1;
        Message fauxMessage = new Message("Je suis un faux message avec une PJ", new Utilisateur());
        Ressource fausseRessource = new Ressource("mock.png", TypeRessource.IMAGE,"http://url.com/mock.png");
        when(messageRepository.trouverParId(idMessage)).thenReturn(fauxMessage);
        when(ressourceRepository.trouverParId(idRessource)).thenReturn(fausseRessource);
        when(messageRepository.sauvegarder(any(Message.class))).thenReturn(fauxMessage);

        // ACT
        serviceMessage.ajouterRessource(idMessage,idRessource);

        // ASSERT
        assertEquals(1, fauxMessage.getRessources().size());
        assertEquals(fausseRessource,fauxMessage.getRessources().get(0));
        verify(messageRepository, times(1)).sauvegarder(fauxMessage);
    }
    @Test
    public void ajouterRessourceMessageInexistantDoitEchouer()
    {
        // ARRANGE
        int idMessage = 1;
        int idRessource = 1;
        when(messageRepository.trouverParId(idMessage)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionMessageIntrouvable.class, () ->
        {
            serviceMessage.ajouterRessource(idMessage,idRessource);
        });
        verify(messageRepository, never()).sauvegarder(any());
    }

    @Test
    public void ajouterRessourceInexistanteDoitEchouer()
    {
        // ARRANGE
        int idMessage = 1;
        int idRessource = 1;
        Message fauxMessage = new Message("Faux Message", new Utilisateur());
        when(messageRepository.trouverParId(idMessage)).thenReturn(fauxMessage);
        when(ressourceRepository.trouverParId(idRessource)).thenReturn(null);

        // ACT & ASSERT
        assertThrows(ExceptionRessourceIntrouvable.class, () ->
        {
            serviceMessage.ajouterRessource(idMessage,idRessource);
        });
    }

    @Test
    public void supprimerRessourceDoitRetirerEtRetournerRessource()
    {
        // ARRANGE
        int idMessage = 1;
        int idRessource = 1;
        Message fauxMessage = new Message("Bonjour", new Utilisateur());
        Ressource fausseRessource = mock(Ressource.class);
        when(fausseRessource.getId_ressource()).thenReturn(idRessource);
        fauxMessage.ajouterRessource(fausseRessource);
        when(messageRepository.trouverParId(idMessage)).thenReturn(fauxMessage);
        when(messageRepository.sauvegarder(any(Message.class))).thenReturn(fauxMessage);

        // ACT
        Ressource resultat = serviceMessage.supprimerRessource(idMessage,idRessource);

        // ASSERT
        assertEquals(fausseRessource,resultat);
        assertTrue(fauxMessage.getRessources().isEmpty());
        verify(messageRepository, times(1)).sauvegarder(fauxMessage);
    }
    @Test
    public void supprimerRessourceMessageInexistantDoitEchouer()
    {
        when(messageRepository.trouverParId(anyInt())).thenReturn(null);
        assertThrows(ExceptionMessageIntrouvable.class, () ->
        {
            serviceMessage.supprimerRessource(1,1);
        });
    }
}
