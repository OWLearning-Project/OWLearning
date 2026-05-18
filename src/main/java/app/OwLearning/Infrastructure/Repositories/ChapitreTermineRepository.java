package app.OwLearning.Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.ChapitreTermine;
import app.OwLearning.Domaine.Interfaces.IChapitreTermineRepository;
import app.OwLearning.Infrastructure.Bdd.JpaChapitreTermineRepository;
import app.OwLearning.Infrastructure.Mapper.ChapitreTermineMapper;
import org.springframework.stereotype.Component;

@Component
public class ChapitreTermineRepository implements IChapitreTermineRepository
{
    private final JpaChapitreTermineRepository jpaRepository;
    private final ChapitreTermineMapper chapitreTermineMapper;

    public ChapitreTermineRepository(JpaChapitreTermineRepository jpaRepository, ChapitreTermineMapper chapitreTermineMapper)
    {
        this.jpaRepository = jpaRepository;
        this.chapitreTermineMapper = chapitreTermineMapper;
    }

    /**
     * Méthode qui permet d'enregister un chapitre terminé en base
     * @param chapitreTermine
     */
    @Override
    public void sauvegarder(ChapitreTermine chapitreTermine)
    {
        this.jpaRepository.save(chapitreTermineMapper.toEntity(chapitreTermine));
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
