package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import app.OwLearning.Infrastructure.Repositories.DiscussionRepository;
import app.OwLearning.Infrastructure.Repositories.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDiscussionRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void trouveDiscussionParIdAvecParticipantsEtMessages()
    {
        int auteurId = insererEleve("auteur-discussion");
        int participantId = insererEleve("participant-discussion");
        int discussionId = insererDiscussion();

        jdbcTemplate.update(
                "INSERT INTO participation_discussion (id_discussion, id_utilisateur) VALUES (?, ?)",
                discussionId,
                auteurId
        );
        jdbcTemplate.update(
                "INSERT INTO participation_discussion (id_discussion, id_utilisateur) VALUES (?, ?)",
                discussionId,
                participantId
        );
        insererMessage(discussionId, auteurId, "Message discussion", "2026-01-01 10:00:00");

        Discussion discussion = discussionRepository.trouverDiscussionParId(discussionId);

        assertThat(discussion.getIdDiscussion()).isEqualTo(discussionId);
        assertThat(discussion.getParticipants())
                .extracting(Utilisateur::getIdUtilisateur)
                .containsExactlyInAnyOrder(auteurId, participantId);
        assertThat(discussion.getMessages())
                .extracting(Message::getContenu)
                .containsExactly("Message discussion");
        assertThat(discussion.getMessages().get(0).getDiscussion()).isSameAs(discussion);
    }

    @Test
    public void sauvegardeDiscussionAvecMessages() throws ExceptionUtilisateurNonAutorise
    {
        int auteurId = insererEleve("test-discussion-auteur");
        int participantId = insererEleve("test-discussion-participant");
        Utilisateur auteur = utilisateurRepository.trouverParId(auteurId);
        Utilisateur participant = utilisateurRepository.trouverParId(participantId);

        Discussion discussion = new Discussion(auteur, participant);
        discussion.ajouterMessage(new Message("Premier message TEST", auteur));

        Discussion sauvegardee = discussionRepository.sauvegarder(discussion);
        synchroniserPersistenceContext();

        Discussion relue = discussionRepository.trouverDiscussionParId(sauvegardee.getIdDiscussion());
        assertThat(relue.getParticipants())
                .extracting(Utilisateur::getIdUtilisateur)
                .containsExactlyInAnyOrder(auteurId, participantId);
        assertThat(relue.getMessages())
                .extracting(Message::getContenu)
                .containsExactly("Premier message TEST");
    }

    @Test
    public void metAJourDiscussionAvecMessage() throws ExceptionUtilisateurNonAutorise
    {
        int auteurId = insererEleve("maj-discussion-auteur");
        int participantId = insererEleve("maj-discussion-participant");
        Utilisateur auteur = utilisateurRepository.trouverParId(auteurId);
        Utilisateur participant = utilisateurRepository.trouverParId(participantId);
        Discussion discussion = new Discussion(auteur, participant);
        discussion.ajouterMessage(new Message("Premier message TEST", auteur));
        Discussion sauvegardee = discussionRepository.sauvegarder(discussion);
        synchroniserPersistenceContext();

        Discussion relue = discussionRepository.trouverDiscussionParId(sauvegardee.getIdDiscussion());

        relue.ajouterMessage(new Message("Second message TEST", participant));
        discussionRepository.sauvegarder(relue);
        synchroniserPersistenceContext();

        Discussion modifiee = discussionRepository.trouverDiscussionParId(sauvegardee.getIdDiscussion());
        assertThat(modifiee.getMessages())
                .extracting(Message::getContenu)
                .containsExactlyInAnyOrder("Premier message TEST", "Second message TEST");
    }
}
