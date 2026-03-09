package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface JpaCoursRepository permettant de récupérer les cours dans la bd
 */
public interface JpaCoursRepository extends JpaRepository<Cours, Integer>
{

    public List<Cours> findByEstPublieTrue();

    public List<Cours> findByCreateurIdUtilisateur(int idCreateur);

    public List<Cours> findByElevesIdUtilisateur(int idEleve);

}
