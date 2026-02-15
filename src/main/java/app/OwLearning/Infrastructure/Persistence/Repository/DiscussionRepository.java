package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Ports.IRepository.IDiscussionRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaDiscussionRepository;
import app.OwLearning.Shared.Exceptions.ExceptionDiscussionInexistante;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe DiscussionRepository pour récupérer les discussions
 */
@Component
public class DiscussionRepository implements IDiscussionRepository
{
    private final JpaDiscussionRepository repositoryJpa;

    /**
     * Constructeur de DiscussionRepository
     * @param repositoryJpa
     */
    public DiscussionRepository(JpaDiscussionRepository repositoryJpa)
    {
        this.repositoryJpa = repositoryJpa;
    }

    /**
     * Méthdode pour trouver les discussions d'un utilisateur
     * @param utilisateurId id de l'utilisateur
     * @return la liste des discussions
     */
    @Override
    public List<Discussion> trouverDiscussionsParUtilisateurId(int utilisateurId)
    {
        return repositoryJpa.findByParticipantsIdUtilisateur(utilisateurId);
    }

    /**
     * Méthode pour sauvegarder ou mettre à jour une discussion
     * @param discussion
     * @return la discussion enregistrée
     */
    @Override
    public Discussion sauvegarder(Discussion discussion)
    {
        return repositoryJpa.save(discussion);
    }

    /**
     * Méthode pour trouver une discussion par son id
     * @param discussionId id de la discussion
     * @return l'objet discussion associé ou lance une exception sinon
     */
    @Override
    public Discussion trouverDiscussionParId(int discussionId)
    {
        return repositoryJpa.findById(discussionId).orElseThrow(() -> new ExceptionDiscussionInexistante("La discussion n'existe pas", discussionId));
    }
}
