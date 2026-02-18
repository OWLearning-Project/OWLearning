package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JpaRessourceRepository extends JpaRepository<Ressource, Integer>
{
    Ressource findByUrl(String url);
}
