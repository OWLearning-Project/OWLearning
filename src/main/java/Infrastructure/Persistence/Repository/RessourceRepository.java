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
        if (ressource.getId_ressource() == 0)
        {
            jpaRessourceRepository.ajouterRessourceNative(
                    ressource.getNom(),
                    ressource.getUrl(),
                    ressource.getType().getLabel()
                    );
            Ressource ressourceSauvegarde = jpaRessourceRepository.trouverParUrlNative(ressource.getUrl());

            if (ressourceSauvegarde == null)
            {
                throw new RuntimeException("Erreur : Impossible de récupérer la ressource insérée.");
            }
            return ressourceSauvegarde;
        }
        else
        {
            jpaRessourceRepository.changerRessourceNative(
                    ressource.getId_ressource(),
                    ressource.getNom(),
                    ressource.getUrl(),
                    ressource.getType().getLabel()
            );
            return ressource;
        }
    }

    public Ressource trouverParId(int id)
    {
        return jpaRessourceRepository.trouverParIdNative(id);
    }
    public void supprimer(int id)
    {
        jpaRessourceRepository.supprimerRessourceNative(id);
    }
}
