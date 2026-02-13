package Infrastructure.Persistence.Interface;

import Domain.Models.Progression;
import Domain.Models.ProgressionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProgressionRepository extends JpaRepository<Progression, ProgressionId> {
}
