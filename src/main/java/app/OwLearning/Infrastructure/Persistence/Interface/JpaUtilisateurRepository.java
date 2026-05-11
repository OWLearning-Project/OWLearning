package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Infrastructure.Persistence.Entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface JpaUtilisateurRepository permettant de récupérer les utilisateurs dans la bd
 */
@Repository
public interface JpaUtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer>
{
    public UtilisateurEntity findByEmail(String email);
}

