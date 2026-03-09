package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface JpaDiscussionRepository permettant de récupérer les discussions dans la bd
 */
@Repository
public interface JpaDiscussionRepository extends JpaRepository<Discussion, Integer>
{
    public List<Discussion> findByParticipantsIdUtilisateur(int id);
}
