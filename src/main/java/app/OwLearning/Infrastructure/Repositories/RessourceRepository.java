package app.OwLearning.Infrastructure.Repositories;


import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Interfaces.IRessourceRepository;
import app.OwLearning.Infrastructure.Entités.RessourceEntity;
import app.OwLearning.Infrastructure.Bdd.JpaRessourceRepository;
import app.OwLearning.Infrastructure.Mapper.RessourceMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class RessourceRepository implements IRessourceRepository
{
    private final JpaRessourceRepository jpaRessourceRepository;
    private final RessourceMapper ressourceMapper;

    public RessourceRepository(JpaRessourceRepository jparessourceRepository, RessourceMapper ressourceMapper)
    {
        this.jpaRessourceRepository = jparessourceRepository;
        this.ressourceMapper = ressourceMapper;
    }
    @Override
    @Transactional
    public Ressource sauvegarder(Ressource ressource)
    {
        RessourceEntity entity = ressourceMapper.toEntity(ressource);
        RessourceEntity saved = jpaRessourceRepository.save(entity);
        return ressourceMapper.toDomain(saved);
    }

    public Ressource trouverParId(int id)
    {
        return jpaRessourceRepository.findById(id).map(ressourceMapper::toDomain).orElse(null);
    }
    public void supprimer(int id)
    {
        jpaRessourceRepository.deleteById(id);
    }
    public Ressource findByUrl(String url)
    {
        RessourceEntity entity = jpaRessourceRepository.findByUrl(url);
        return entity != null ? ressourceMapper.toDomain(entity) : null;
    }
}
