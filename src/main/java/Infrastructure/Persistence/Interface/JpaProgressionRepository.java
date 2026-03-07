package Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.Progression;
import app.OwLearning.Domain.Models.ProgressionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProgressionRepository extends JpaRepository<Progression, ProgressionId> {
}
