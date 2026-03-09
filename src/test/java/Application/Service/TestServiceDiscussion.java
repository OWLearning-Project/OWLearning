package Application.Service;

import app.OwLearning.Application.Services.ServiceDiscussion;
import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.IDiscussionRepository;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceDiscussion;
import app.OwLearning.Shared.Exceptions.ExceptionDiscussionInexistante;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
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
public class TestServiceDiscussion
{
    @Mock
    private IDiscussionRepository repositoryDiscussion;

    @Mock
    private IUtilisateurRepository repositoryUtilisateur;

    @InjectMocks
    private ServiceDiscussion serviceDiscussion;

    @Test
    public void recupereDiscussionDeLUtilisateur()
    {
        // Act
        int utilisateurId = 4;
        Utilisateur utilisateur1 = new Createur("createur", "1", "1@createur.com", "1234");
        Utilisateur utilisateur2 = new Eleve("eleve", "bob", "bob@eleve.com", "5678");
        Utilisateur utilisateur3 = new Createur("createur", "2", "2@createur.com", "91011");

        List<Discussion> listeAttendue = new ArrayList<Discussion>();
        listeAttendue.add(new Discussion(utilisateur1, utilisateur2));
        listeAttendue.add(new Discussion(utilisateur2, utilisateur3));

        when(repositoryDiscussion.trouverDiscussionsParUtilisateurId(utilisateurId)).thenReturn(listeAttendue);

        // Act
        List<Discussion> listeRecuperee = repositoryDiscussion.trouverDiscussionsParUtilisateurId(utilisateurId);

        // Assert
        assertEquals(listeAttendue, listeRecuperee);
        assertNotNull(listeRecuperee);
        verify(repositoryDiscussion, times(1)).trouverDiscussionsParUtilisateurId(utilisateurId);
    }

    @Test
    public void utilisateurAPasDeDiscussion()
    {
        // Act
        int utilisateurId = 3;
        List<Discussion> listeAttendue = new ArrayList<Discussion>();

        when(repositoryDiscussion.trouverDiscussionsParUtilisateurId(utilisateurId)).thenReturn(listeAttendue);

        // Act
        List<Discussion> listeRecuperee = serviceDiscussion.getDiscussionsParIdUtilisateur(utilisateurId);

        // Assert
        assertEquals(0, listeRecuperee.size());
        assertEquals(listeAttendue, listeRecuperee);
        verify(repositoryDiscussion, times(1)).trouverDiscussionsParUtilisateurId(utilisateurId);
    }

    @Test
    public void messageEnvoye() throws ExceptionUtilisateurNonAutorise
    {
        // Arrange
        int discussionId = 4;
        int auteurId = 2;
        String contenu = "ceci est un message";

        Utilisateur utilisateur1 = new Createur("createur", "1", "1@createur.com", "1234");
        Utilisateur utilisateur2 = new Eleve("eleve", "bob", "bob@eleve.com", "5678");
        Discussion discussion = new Discussion(utilisateur1, utilisateur2);

        when(repositoryDiscussion.trouverDiscussionParId(discussionId)).thenReturn(discussion);
        when(repositoryUtilisateur.trouverParId(auteurId)).thenReturn(utilisateur2);
        when(repositoryDiscussion.sauvegarder(any(Discussion.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Discussion discussionSauvegardee = serviceDiscussion.envoyerMessage(discussionId, auteurId, contenu);

        // Assert
        assertNotNull(discussionSauvegardee);
        assertEquals(1, discussion.getMessages().size());
        assertEquals(contenu, discussion.getMessages().get(0).getContenu());
        assertEquals(utilisateur2, discussion.getMessages().get(0).getUtilisateur());

        verify(repositoryDiscussion, times(1)).trouverDiscussionParId(discussionId);
        verify(repositoryDiscussion, times(1)).sauvegarder(any(Discussion.class));
    }

    @Test
    public void envoyerMessageAvecDiscussionInexistante()
    {
        // Arrange
        int discussionId = 4;
        int auteurId = 2;
        String contenu = "ceci est un message";

        when(repositoryDiscussion.trouverDiscussionParId(discussionId)).thenThrow(new ExceptionDiscussionInexistante("La discussion n'existe pas", discussionId));

        // Act & Assert
        assertThrows(ExceptionDiscussionInexistante.class, () -> serviceDiscussion.envoyerMessage(discussionId, auteurId, contenu));
        verify(repositoryDiscussion, times(1)).trouverDiscussionParId(discussionId);
    }

    @Test
    public void envoyerMessageAvecAuteurInexistant()
    {
        // Arrange
        int discussionId = 4;
        int auteurId = 2;
        String contenu = "ceci est un message";

        Utilisateur utilisateur1 = new Createur("createur", "1", "1@createur.com", "1234");
        Utilisateur utilisateur2 = new Eleve("eleve", "bob", "bob@eleve.com", "5678");
        Discussion discussion = new Discussion(utilisateur1, utilisateur2);

        when(repositoryDiscussion.trouverDiscussionParId(discussionId)).thenReturn(discussion);
        when(repositoryUtilisateur.trouverParId(auteurId)).thenThrow(new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", auteurId));

        // Act & Assert
        assertThrows(ExceptionUtilisateurInexistant.class, () -> serviceDiscussion.envoyerMessage(discussionId, auteurId, contenu));
        verify(repositoryDiscussion, times(1)).trouverDiscussionParId(discussionId);
        verify(repositoryUtilisateur, times(1)).trouverParId(auteurId);
    }
}
