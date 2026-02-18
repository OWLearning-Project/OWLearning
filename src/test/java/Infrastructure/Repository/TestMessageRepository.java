package Infrastructure.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaMessageRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        messageNouveau = new Message("Contenu Test", auteur);
        messageNouveau.setDateCreation(Timestamp.from(Instant.now()));
        messageNouveau.setStatutMessage(StatutMessage.ENVOI);
        messageNouveau.setDiscussion(discussionMock);

    }

    @Test
    public void sauvegarderNouveauMessageDoitAppelerSaveJPA()
    {
        // ARRANGE
        Message messageSauvegarde = new Message("Contenu Test", auteur);
        when(jpaMessageRepository.save(messageNouveau)).thenReturn(messageSauvegarde);

        // ACT
        Message resultat = messageRepository.sauvegarder(messageNouveau);

        // ASSERT
        verify(jpaMessageRepository).save(messageNouveau);
        assertEquals(messageSauvegarde,resultat);
    }

    @Test
    public void trouverParDiscussionDoitRetournerListe() {
        // ARRANGE
        ArrayList<Message> listeAttendue = new ArrayList<>();
        listeAttendue.add(messageNouveau);

        when(jpaMessageRepository.findByDiscussionIdOrderByDateCreationAsc(1)).thenReturn(listeAttendue);

        // ACT
        List<Message> resultat = messageRepository.trouverParDiscussion(1);

        // ASSERT
        assertEquals(1, resultat.size());
        verify(jpaMessageRepository).findByDiscussionIdOrderByDateCreationAsc(1);
    }
    @Test
    public void trouverParIdDoitRetournerMessage()
    {
        // ARRANGE

        when(jpaMessageRepository.findById(55)).thenReturn(Optional.of(messageNouveau));

        // ACT
        Message resultat = messageRepository.trouverParId(55);

        // ASSERT
        assertNotNull(resultat);
        assertEquals(messageNouveau, resultat);
    }
}