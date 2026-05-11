package app.OwLearning.Infrastructure.Persistence.Interface;


import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface JpaChapitreRepository permettant de récupérer les chapitres dans la bd
 */
@Repository
public interface JpaChapitreRepository extends JpaRepository<ChapitreEntity,Integer> {

}
