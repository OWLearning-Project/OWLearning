package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface JpaCoursRepository permettant de récupérer les cours dans la bd avec des requêtes SQL natives
 */
public interface JpaCoursRepository extends JpaRepository<Cours, Integer>
{

    /**
     * @return la liste des cours publiés
     */
    public List<Cours> findByEstPublieTrue();

    public List<Cours> findByCreateurIdUtilisateur(int idCreateur);

    public List<Cours> findByElevesIdUtilisateur(int idEleve);

}
