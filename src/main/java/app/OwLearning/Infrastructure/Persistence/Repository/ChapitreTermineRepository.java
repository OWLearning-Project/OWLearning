package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.ChapitreTermine;
import app.OwLearning.Domain.Ports.IRepository.IChapitreTermineRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaChapitreTermineRepository;
import org.springframework.stereotype.Component;

@Component
public class ChapitreTermineRepository implements IChapitreTermineRepository
{
    private final JpaChapitreTermineRepository jpaRepository;

    public ChapitreTermineRepository(JpaChapitreTermineRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Méthode qui permet d'enregister un chapitre terminé en base
     * @param chapitreTermine
     */
    @Override
    public void sauvegarder(ChapitreTermine chapitreTermine)
    {
        this.jpaRepository.save(chapitreTermine);
    }

    /**
     * Méthode qui permet de vérifier si la ligne (id_chapitre, id_eleve) existe déjà en basee
     * @param idChapitre
     * @param idEleve
     * @return true si la ligne existe déjà, false sinon
     */
    @Override
    public boolean existe(int idChapitre, int idEleve)
    {
        return this.jpaRepository.existsByChapitreTermineIdIdChapitreAndIdEleve(idChapitre,idEleve);
    }
}
