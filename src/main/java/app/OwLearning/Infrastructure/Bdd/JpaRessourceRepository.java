package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.RessourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JpaRessourceRepository extends JpaRepository<RessourceEntity, Integer>
{
    RessourceEntity findByUrl(String url);
}
