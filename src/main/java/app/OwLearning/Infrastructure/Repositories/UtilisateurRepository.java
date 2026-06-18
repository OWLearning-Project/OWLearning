package app.OwLearning.Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Entités.CreateurEntity;
import app.OwLearning.Infrastructure.Entités.EleveEntity;
import app.OwLearning.Infrastructure.Entités.UtilisateurEntity;
import app.OwLearning.Infrastructure.Bdd.JpaUtilisateurRepository;
import app.OwLearning.Infrastructure.Mapper.CreateurMapper;
import app.OwLearning.Infrastructure.Mapper.EleveMapper;
import app.OwLearning.Infrastructure.Mapper.UtilisateurMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe UtilisateurRepository qui permet de récupérer les utilisateurs
 */
@Component
public class UtilisateurRepository implements IUtilisateurRepository
{
    private final JpaUtilisateurRepository jpaRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final EleveMapper eleveMapper;
    private final CreateurMapper createurMapper;

    /**
     * Constructeur de UtilisateurRepository
     * @param jpaRepository
     */
    public UtilisateurRepository (JpaUtilisateurRepository jpaRepository, UtilisateurMapper utilisateurMapper, EleveMapper eleveMapper, CreateurMapper createurMapper)
    {
        this.jpaRepository = jpaRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.eleveMapper = eleveMapper;
        this.createurMapper = createurMapper;
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
     * Méthode qui permet d'insérer ou de mettre à jour un utilisateur en base
     * @param utilisateur
     * @return l'utilisateur sauvegardé
     */
    @Override
    public Utilisateur sauvegarder(Utilisateur utilisateur)
    {
        if (utilisateur.getIdUtilisateur() != 0)
        {
            UtilisateurEntity entiteEnBase = jpaRepository.findById(utilisateur.getIdUtilisateur())
                    .orElseThrow(() -> new RuntimeException("Erreur: Utilisateur introuvable pour la mise à jour"));

            if (utilisateur instanceof Eleve && entiteEnBase instanceof EleveEntity)
            {
                eleveMapper.updateEntityFromDomain((Eleve) utilisateur, (EleveEntity) entiteEnBase);
            }
            else if (utilisateur instanceof Createur && entiteEnBase instanceof CreateurEntity)
            {
                createurMapper.updateEntityFromDomain((Createur) utilisateur, (CreateurEntity) entiteEnBase);
            }
            else
            {
                utilisateurMapper.updateEntityFromDomain(utilisateur, entiteEnBase);
            }

            UtilisateurEntity sauvegarde = jpaRepository.save(entiteEnBase);
            return utilisateurMapper.toDomain(sauvegarde);
        }
        else
        {
            UtilisateurEntity entity = utilisateurMapper.toEntity(utilisateur);
            UtilisateurEntity saved = jpaRepository.save(entity);
            return utilisateurMapper.toDomain(saved);
        }
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

    @Override
    public List<Utilisateur> trouverCreateurs()
    {
        return jpaRepository.findAllCreateurs()
                .stream()
                .map(utilisateurMapper::toDomain)
                .toList();
    }

    public List<Utilisateur> findAll()
    {
        return jpaRepository.findAll()
                .stream()
                .map(utilisateurMapper::toDomain)
                .toList();
    }
}
