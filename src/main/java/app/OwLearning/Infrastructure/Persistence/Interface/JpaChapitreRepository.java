package app.OwLearning.Infrastructure.Persistence.Interface;


import app.OwLearning.Domain.Models.Chapitre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaChapitreRepository extends JpaRepository<Chapitre,Integer> {

}
