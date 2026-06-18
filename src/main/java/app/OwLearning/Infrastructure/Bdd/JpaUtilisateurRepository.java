package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface JpaUtilisateurRepository permettant de récupérer les utilisateurs dans la bd
 */
@Repository
public interface JpaUtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer>
{
    public UtilisateurEntity findByEmail(String email);

    @Query("SELECT c FROM CreateurEntity c ORDER BY c.nom ASC, c.prenom ASC")
    public List<UtilisateurEntity> findAllCreateurs();
}

