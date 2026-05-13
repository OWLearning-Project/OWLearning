package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Ports.IRepository.IDiscussionRepository;
import app.OwLearning.Infrastructure.Persistence.Entity.DiscussionEntity;
import app.OwLearning.Infrastructure.Persistence.Entity.MessageEntity;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaDiscussionRepository;
import app.OwLearning.Infrastructure.Persistence.Mapper.DiscussionMapper;
import app.OwLearning.Infrastructure.Persistence.RelationReconstructor;
import app.OwLearning.Domain.Exceptions.ExceptionDiscussionInexistante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
/**
 * Classe DiscussionRepository pour récupérer les discussions
 */
@Component
public class DiscussionRepository implements IDiscussionRepository
{
    private final JpaDiscussionRepository repositoryJpa;
    private final DiscussionMapper discussionMapper;
    private final RelationReconstructor relationReconstructor;

    /**
     * Constructeur de DiscussionRepository
     * @param repositoryJpa
     */
    public DiscussionRepository(JpaDiscussionRepository repositoryJpa, DiscussionMapper discussionMapper, RelationReconstructor relationReconstructor)
    {
        this.repositoryJpa = repositoryJpa;
        this.discussionMapper = discussionMapper;
        this.relationReconstructor = relationReconstructor;
    }

    /**
     * Méthdode pour trouver les discussions d'un utilisateur
     * @param utilisateurId id de l'utilisateur
     * @return la liste des discussions
     */
    @Override
    public List<Discussion> trouverDiscussionsParUtilisateurId(int utilisateurId)
    {
        return repositoryJpa.findByParticipantsIdUtilisateur(utilisateurId)
                .stream()
                .map(this::toDomainAvecRelations)
                .toList();
    }

    /**
     * Méthode pour sauvegarder ou mettre à jour une discussion
     * @param discussion
     * @return la discussion enregistrée
     */
    @Override
    public Discussion sauvegarder(Discussion discussion)
    {
        DiscussionEntity entity = discussionMapper.toEntity(discussion);
        if (entity.getMessages() != null)
        {
            for (int i=0; i<entity.getMessages().size(); i++)
            {
                MessageEntity message = entity.getMessages().get(i);
                message.setDiscussion(entity);
            }
        }
        DiscussionEntity saved = repositoryJpa.save(entity);

        return toDomainAvecRelations(saved);
    }

    /**
     * Méthode pour trouver une discussion par son id
     * @param discussionId id de la discussion
     * @return l'objet discussion associé ou lance une exception sinon
     */
    @Override
    public Discussion trouverDiscussionParId(int discussionId)
    {
        DiscussionEntity entity = repositoryJpa.findById(discussionId)
                .orElseThrow((() -> new ExceptionDiscussionInexistante("La discussion n'existe pas", discussionId)));

        return toDomainAvecRelations(entity);
    }

    private Discussion toDomainAvecRelations(DiscussionEntity entity)
    {
        Discussion discussion = discussionMapper.toDomain(entity);

        relationReconstructor.reconstructDiscussionMessages(discussion);
        return discussion;
    }
}
