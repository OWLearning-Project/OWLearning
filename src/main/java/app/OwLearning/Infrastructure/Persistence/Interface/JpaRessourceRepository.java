package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Infrastructure.Persistence.Entity.RessourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JpaRessourceRepository extends JpaRepository<RessourceEntity, Integer>
{
    RessourceEntity findByUrl(String url);
}
