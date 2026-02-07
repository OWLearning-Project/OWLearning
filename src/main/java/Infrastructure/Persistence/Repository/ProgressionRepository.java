package Infrastructure.Persistence.Repository;

import Domain.Models.Progression;
import Domain.Ports.IRepository.IProgressionRepository;
import Infrastructure.Persistence.Interface.JpaProgressionRepository;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import org.springframework.stereotype.Component;

@Component
public class ProgressionRepository implements IProgressionRepository {

    private final JpaProgressionRepository jpaProgressionRepository;

    public ProgressionRepository(JpaProgressionRepository unJpaProgressionRepository){
        this.jpaProgressionRepository = unJpaProgressionRepository;
    }

    /**
     * Méthode pour trouver une progression grâce à un eleveId et un coursId
     * @param elevId id de l'élève
     * @param coursId id du cours
     * @return un objet Progression
     */
    @Override
    public Progression trouverParId(int elevId, int coursId) {
        return jpaProgressionRepository.findByEleveAndCoursNative(elevId, coursId);
    }
}
