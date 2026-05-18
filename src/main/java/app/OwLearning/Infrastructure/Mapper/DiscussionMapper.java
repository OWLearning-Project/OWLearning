package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Infrastructure.Entités.DiscussionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UtilisateurMapper.class, MessageMapper.class})
public interface DiscussionMapper
{
    Discussion toDomain(DiscussionEntity entity);
    List<Discussion> toDomain(List<DiscussionEntity> entities);

    DiscussionEntity toEntity(Discussion discussion);
    List<DiscussionEntity> toEntity(List<Discussion> discussions);

    @Mapping(target = "idDiscussion", ignore = true)
    void updateEntityFromDomain(Discussion discussion, @MappingTarget DiscussionEntity entity);
}
