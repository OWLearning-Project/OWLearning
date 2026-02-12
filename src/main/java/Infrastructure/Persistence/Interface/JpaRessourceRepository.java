package Infrastructure.Persistence.Interface;

import Domain.Models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JpaRessourceRepository extends JpaRepository<Ressource, Integer>
{
    Ressource findByUrl(String url);
}
