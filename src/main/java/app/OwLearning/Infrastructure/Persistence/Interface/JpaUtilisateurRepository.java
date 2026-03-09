package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Interface JpaUtilisateurRepository permettant de récupérer les utilisateurs dans la bd
 */
@Repository
public interface JpaUtilisateurRepository extends JpaRepository<Utilisateur, Integer>
{
    public Utilisateur findByEmail(String email);
}

