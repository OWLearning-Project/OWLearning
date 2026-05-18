package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.CoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface JpaCoursRepository permettant de récupérer les cours dans la bd
 */
public interface JpaCoursRepository extends JpaRepository<CoursEntity, Integer>
{

    public List<CoursEntity> findByEstPublieTrue();

    public List<CoursEntity> findByCreateurIdUtilisateur(int idCreateur);

    public List<CoursEntity> findByElevesIdUtilisateur(int idEleve);

}
