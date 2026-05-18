package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.UtilisateurEntity;
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

