package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Entity.UtilisateurEntity;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Mapper.UtilisateurMapper;
import org.springframework.stereotype.Component;

/**
 * Classe UtilisateurRepository qui permet de récupérer les utilisateurs
 */
@Component
public class UtilisateurRepository implements IUtilisateurRepository
{
    private final JpaUtilisateurRepository jpaRepository;
    private final UtilisateurMapper utilisateurMapper;

    /**
     * Constructeur de UtilisateurRepository
     * @param jpaRepository
     */
    public UtilisateurRepository (JpaUtilisateurRepository jpaRepository, UtilisateurMapper utilisateurMapper)
    {
        this.jpaRepository = jpaRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Méthode qui permet de trouver un Utilisateur vie son email
     * @param email
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParEmail(String email)
    {
        UtilisateurEntity entity = jpaRepository.findByEmail(email);
        return entity != null ? utilisateurMapper.toDomain(entity) : null;
    }

    /**
     * Méthode qui permet d'insérer un utilisateur en base
     * @param utilisateur
     * @return l'utilisateur inséré
     */
    @Override
    public Utilisateur sauvegarder(Utilisateur utilisateur)
    {
        UtilisateurEntity entity = utilisateurMapper.toEntity(utilisateur);
        UtilisateurEntity saved = jpaRepository.save(entity);
        return utilisateurMapper.toDomain(saved);
    }

    /**
     * Cette méthode permet de trouver un utilisateur via son identifiant
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParId(int id)
    {
        return jpaRepository.findById(id).map(utilisateurMapper::toDomain).orElse(null);
    }
}
