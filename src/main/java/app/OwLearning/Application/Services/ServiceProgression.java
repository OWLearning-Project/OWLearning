package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Progression;
import app.OwLearning.Domain.Ports.IRepository.IProgressionRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceProgression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j

/**
 * Classe ServiceProgression, permet de gérer les traitements liés à la progression
 */
@Service
public class ServiceProgression implements IServiceProgression {

    private final IProgressionRepository progressionRepository;

    /**
     * Constructeur de ServiceProgression
     * @param progressionRepository
     */
    public ServiceProgression(IProgressionRepository progressionRepository) {
        this.progressionRepository = progressionRepository;
    }

    /**
     * Méthode qui permet de récupérer le taux de progression d'un élève sur un cours
     * @param eleveId id de l'élève
     * @param coursId id du cours
     * @return un float qui correspond au taux de progression
     */
    @Override
    public float getProgressionEleve(int eleveId, int coursId) {
        log.info("Récupération de la progression pour l'élève {} sur le cours {}", eleveId, coursId);
        Progression progression = progressionRepository.trouverParId(eleveId, coursId);
        log.info("taux de progression renvoyé {}", progression);
        return progression.getTauxProgression();
    }
}
