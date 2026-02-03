package Domain.Ports.IRepository;

import Domain.Models.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapitreRepository extends JpaRepository<Chapitre,Integer> {

}
