package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IDiscussionRepository;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceDiscussion;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Classe ServiceDiscussion, permet de gérer les traitements de discussions
 */
@Service
public class ServiceDiscussion implements IServiceDiscussion
{
    private final IDiscussionRepository repositoryDiscussion;
    private final IUtilisateurRepository repositoryUtilisateur;

    /**
     * Constructeur de ServiceDiscussion
     * @param repositoryDiscussion
     * @param repositoryUtilisateur
     */
    public ServiceDiscussion(IDiscussionRepository repositoryDiscussion, IUtilisateurRepository repositoryUtilisateur)
    {
        this.repositoryDiscussion = repositoryDiscussion;
        this.repositoryUtilisateur = repositoryUtilisateur;
    }

    /**
     * Méthode qui permet de récupérer les discussions d'un utilisateur
     * @param idUtilisateur id de l'utilisateur
     * @return la liste des discussions
     */
    @Override
    public List<Discussion> getDiscussionsParIdUtilisateur(int idUtilisateur)
    {
        return this.repositoryDiscussion.trouverDiscussionsParUtilisateurId(idUtilisateur);
    }

    /**
     * Méthode qui permet d'envoyer un message d'un utilisateur
     * @param discussionId id de la discussion
     * @param auteurId id de l'utilisateur auteur
     * @param contenu contenu du message
     * @return la discussion avec le nouveau message envoyé
     * @throws ExceptionUtilisateurNonAutorise
     */
    @Override
    @Transactional
    public Discussion envoyerMessage(int discussionId, int auteurId, String contenu) throws ExceptionUtilisateurNonAutorise
    {
        Discussion discussion = this.repositoryDiscussion.trouverDiscussionParId(discussionId);
        Utilisateur auteur = this.repositoryUtilisateur.trouverParId(auteurId);
        Message message = new Message(contenu, auteur);
        discussion.ajouterMessage(message);
        discussion.getMessages().size();
        return this.repositoryDiscussion.sauvegarder(discussion);
    }
}
