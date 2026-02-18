package Infrastructure.Repository;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaDiscussionRepository;
import app.OwLearning.Infrastructure.Persistence.Repository.DiscussionRepository;
import app.OwLearning.Shared.Exceptions.ExceptionDiscussionInexistante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDiscussionRepository
{
    @Mock
    private JpaDiscussionRepository repositoryJpa;

    @InjectMocks
    private DiscussionRepository repository;

    @Test
    public void retourneLaListeDesDiscussionsRecupereesParIdUtilisateur()
    {
        // Arrange
        int idUtilisateur = 3;

        List<Discussion> discussionsAttendues = new ArrayList<Discussion>();
        discussionsAttendues.add(new Discussion());
        discussionsAttendues.add(new Discussion());

        when(repositoryJpa.findByParticipantsIdUtilisateur(idUtilisateur)).thenReturn(discussionsAttendues);

        // Act
        List<Discussion> discussionsRecuperees = repository.trouverDiscussionsParUtilisateurId(idUtilisateur);

        // Assert
        assertEquals(discussionsAttendues, discussionsRecuperees);
        assertEquals(discussionsAttendues.size(), discussionsRecuperees.size());
        assertNotNull(discussionsRecuperees);
        verify(repositoryJpa, times(1)).findByParticipantsIdUtilisateur(idUtilisateur);
    }

    @Test
    public void doitRetournerUneListeVideDeDiscussionsCarPasDeDiscussionTrouvee()
    {
        // Arrange
        int idUtilisateur = 139;

        List<Discussion> discussionsAttendues = List.of();

        when(repositoryJpa.findByParticipantsIdUtilisateur(idUtilisateur)).thenReturn(discussionsAttendues);

        // Act
        List<Discussion> discussionsRecuperees = repository.trouverDiscussionsParUtilisateurId(idUtilisateur);

        // Assert
        assertEquals(discussionsAttendues, discussionsRecuperees);
        assertEquals(0, discussionsRecuperees.size());
        assertNotNull(discussionsRecuperees);
        verify(repositoryJpa, times(1)).findByParticipantsIdUtilisateur(idUtilisateur);
    }

    @Test
    public void doitSauvegarderLaDiscussion()
    {
        // Arrange
        Discussion discussion = new Discussion();

        when(repositoryJpa.save(discussion)).thenReturn(discussion);

        // Act
        Discussion discussionSauvegardee = repository.sauvegarder(discussion);

        // Assert
        assertEquals(discussion, discussionSauvegardee);
        assertNotNull(discussionSauvegardee);
        verify(repositoryJpa, times(1)).save(discussion);
    }

    @Test
    public void doitLancerUneExceptionCarNeSauvegardePasDiscussion()
    {
        // Arrange
        Discussion discussion = new Discussion();

        when(repositoryJpa.save(discussion)).thenThrow(new RuntimeException("Erreur de sauvegarde"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> repository.sauvegarder(discussion));
        verify(repositoryJpa, times(1)).save(discussion);
    }

    @Test
    public void doitRetournerUneDiscussionTrouveeParId()
    {
        // Arrange
        int discussionId = 29;
        Discussion discussionAttendue = new Discussion();

        when(repositoryJpa.findById(discussionId)).thenReturn(Optional.of(discussionAttendue));

        // Act
        Discussion discussionRecuperee = repository.trouverDiscussionParId(discussionId);

        // Assert
        assertEquals(discussionAttendue, discussionRecuperee);
        assertNotNull(discussionRecuperee);
        verify(repositoryJpa, times(1)).findById(discussionId);
    }

    @Test
    public void doitLancerUneExceptionDiscussionInexistanteCarPasDeDiscussionParId()
    {
        // Arrange
        int discussionId = 99;

        when(repositoryJpa.findById(discussionId)).thenReturn(Optional.empty());

        // Act & Arrange
        assertThrows(ExceptionDiscussionInexistante.class, () -> repository.trouverDiscussionParId(discussionId));
        verify(repositoryJpa, times(1)).findById(discussionId);
    }
}
