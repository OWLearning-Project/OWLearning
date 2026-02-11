package Infrastructure.Persistence.Interface;

import Domain.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface permettant de réutiliser une interface de JPA pour redfinir nos requêtes SQL
 */
@Repository
public interface JpaUtilisateurRepository extends JpaRepository<Utilisateur, Integer>
{
    public Utilisateur findByEmail(String email);
}
