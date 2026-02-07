package Infrastructure.Persistence.Interface;

import Domain.Models.Progression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProgressionRepository extends JpaRepository<Progression, Integer> {

    /**
     *Retrouve la progression d'un élève pour un cours précis
     * @param idEleve id le l'élève
     * @param idCours id du cours
     * @return la progression contenant le taux calculé, ou null si l'élève n'est pas inscrit à ce cours
     */
    @Query(value = "SELECT * FROM progression WHERE id_eleve = :idEleve and id_cours = :idCours", nativeQuery = true)
    Progression findByEleveAndCoursNative(@Param("idEleve") int idEleve, @Param("idCours") int idCours);
}
