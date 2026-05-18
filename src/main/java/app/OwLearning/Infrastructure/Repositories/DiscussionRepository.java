package app.OwLearning.Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Interfaces.IDiscussionRepository;
import app.OwLearning.Infrastructure.Entités.DiscussionEntity;
import app.OwLearning.Infrastructure.Entités.MessageEntity;
import app.OwLearning.Infrastructure.Bdd.JpaDiscussionRepository;
import app.OwLearning.Infrastructure.Mapper.DiscussionMapper;
import app.OwLearning.Infrastructure.Helper.RelationReconstructor;
import app.OwLearning.Domaine.Exceptions.ExceptionDiscussionInexistante;
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
