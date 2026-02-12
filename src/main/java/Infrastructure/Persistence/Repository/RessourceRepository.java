package Infrastructure.Persistence.Repository;

import Domain.Models.Ressource;
import Domain.Ports.IRepository.IRessourceRepository;
import Infrastructure.Persistence.Interface.JpaRessourceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class RessourceRepository implements IRessourceRepository
{
    private final JpaRessourceRepository jpaRessourceRepository;

    public RessourceRepository(JpaRessourceRepository jparessourceRepository)
    {
        this.jpaRessourceRepository = jparessourceRepository;
    }
    @Override
    @Transactional
    public Ressource sauvegarder(Ressource ressource)
    {
        return jpaRessourceRepository.save(ressource);
    }

    public Ressource trouverParId(int id)
    {
        return jpaRessourceRepository.findById(id).orElse(null);
    }
    public void supprimer(int id)
    {
        jpaRessourceRepository.deleteById(id);
    }
    public Ressource findByUrl(String url)
    {
        return jpaRessourceRepository.findByUrl(url);
    }
}
