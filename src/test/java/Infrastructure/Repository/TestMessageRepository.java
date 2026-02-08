package Infrastructure.Repository;

import Domain.Models.*;
import Infrastructure.Persistence.Interface.JpaMessageRepository;
import Infrastructure.Persistence.Repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMessageRepository {

    @Mock
    private JpaMessageRepository jpaMessageRepository;

    @InjectMocks
    private MessageRepository messageRepository;

    private Message messageNouveau;
    private Utilisateur auteur;

    @BeforeEach
    void setUp()
    {
        auteur = new Utilisateur("Nom", "Prenom", "email", "mdp");

        Discussion discussionMock = mock(Discussion.class);
        lenient().when(discussionMock.getId()).thenReturn(99);

        messageNouveau = new Message("Contenu Test", auteur);
        messageNouveau.setDate_creation(Timestamp.from(Instant.now()));
        messageNouveau.setStatutMessage(StatutMessage.ENVOI);
        messageNouveau.setDiscussion(discussionMock);

    }

    @Test
    public void sauvegarderNouveauMessageDoitInsererRecupererIdEtLierRessources()
    {
        // ARRANGE
        Ressource pjMock = mock(Ressource.class);
        when(pjMock.getId_ressource()).thenReturn(10);
        messageNouveau.ajouterRessource(pjMock);

        Message message = mock(Message.class);
        when(message.getId_message()).thenReturn(55);
        when(jpaMessageRepository.trouverParAuteurEtParDateNative(anyInt(), any(Timestamp.class)))
                .thenReturn(message);
        // ACT
        Message resultat = messageRepository.sauvegarder(messageNouveau);

        // ASSERT
        verify(jpaMessageRepository).ajouterMessageNative(
                eq("Contenu Test"),
                any(Timestamp.class),
                eq("Envoi"),
                eq(99),
                anyInt()
        );

        verify(jpaMessageRepository).ajouterLiaisonPieceJointeNative(55, 10);
        assertEquals(message, resultat);
    }

    @Test
    public void trouverParDiscussionDoitRetournerListe() {
        // ARRANGE
        ArrayList<Message> listeAttendue = new ArrayList<>();
        listeAttendue.add(messageNouveau);

        when(jpaMessageRepository.trouverParDiscussionIdNative(1)).thenReturn(listeAttendue);

        // ACT
        ArrayList<Message> resultat = messageRepository.trouverParDiscussion(1);

        // ASSERT
        assertEquals(1, resultat.size());
        verify(jpaMessageRepository).trouverParDiscussionIdNative(1);
    }

    @Test
    public void sauvegarderEchecRecuperationIdDoitLeverException() {
        // ARRANGE
        when(jpaMessageRepository.trouverParAuteurEtParDateNative(anyInt(), any(Timestamp.class)))
                .thenReturn(null);

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> messageRepository.sauvegarder(messageNouveau));
    }
}