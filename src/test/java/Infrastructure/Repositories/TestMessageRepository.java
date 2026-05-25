package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Infrastructure.Repositories.MessageRepository;
import app.OwLearning.Infrastructure.Repositories.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMessageRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void trouveMessageParId()
    {
        int auteurId = insererEleve("auteur-message");
        int discussionId = insererDiscussion();
        int premierMessageId = insererMessage(discussionId, auteurId, "Premier message", "2026-01-01 10:00:00");

        Message message = messageRepository.trouverParId(premierMessageId);

        assertThat(message).isNotNull();
        assertThat(message.getId_message()).isEqualTo(premierMessageId);
        assertThat(message.getUtilisateur().getIdUtilisateur()).isEqualTo(auteurId);
    }

    @Test
    public void retourneNullQuandMessageIntrouvable()
    {
        assertThat(messageRepository.trouverParId(999)).isNull();
    }

    @Test
    public void trouveMessagesParDiscussion()
    {
        int auteurId = insererEleve("auteur-message-discussion");
        int discussionId = insererDiscussion();
        int premierMessageId = insererMessage(discussionId, auteurId, "Premier message", "2026-01-01 10:00:00");
        int secondMessageId = insererMessage(discussionId, auteurId, "Second message", "2026-01-01 10:05:00");

        List<Message> messages = messageRepository.trouverParDiscussion(discussionId);

        assertThat(messages)
                .extracting(Message::getId_message)
                .containsExactly(premierMessageId, secondMessageId);
    }

    @Test
    public void retourneListeVideQuandDiscussionSansMessage()
    {
        int discussionId = insererDiscussion();

        List<Message> messages = messageRepository.trouverParDiscussion(discussionId);

        assertThat(messages).isEmpty();
    }

    @Test
    public void sauvegardeMessage()
    {
        int auteurId = insererEleve("test-message-auteur");
        Message nouveauMessage = new Message("Message avant modification", utilisateurRepository.trouverParId(auteurId));

        Message message = messageRepository.sauvegarder(nouveauMessage);
        synchroniserPersistenceContext();

        assertThat(message.getId_message()).isGreaterThan(0);
        assertThat(messageRepository.trouverParId(message.getId_message()).getContenu())
                .isEqualTo("Message avant modification");
    }

    @Test
    public void metAJourMessage()
    {
        int auteurId = insererEleve("maj-message-auteur");
        Message nouveauMessage = new Message("Message avant modification", utilisateurRepository.trouverParId(auteurId));
        Message message = messageRepository.sauvegarder(nouveauMessage);
        synchroniserPersistenceContext();

        message.setContenu("Message modifie");

        messageRepository.sauvegarder(message);
        synchroniserPersistenceContext();

        String contenu = jdbcTemplate.queryForObject(
                "SELECT contenu FROM message WHERE id_message = ?",
                String.class,
                message.getId_message()
        );

        assertThat(contenu).isEqualTo("Message modifie");
    }

    @Test
    public void supprimeMessage()
    {
        int auteurId = insererEleve("suppression-message-auteur");
        Message nouveauMessage = new Message("Message a supprimer", utilisateurRepository.trouverParId(auteurId));
        Message message = messageRepository.sauvegarder(nouveauMessage);
        synchroniserPersistenceContext();

        messageRepository.supprimer(message.getId_message());
        synchroniserPersistenceContext();
        assertThat(messageRepository.trouverParId(message.getId_message())).isNull();
    }
}
