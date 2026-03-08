package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Progression;
import app.OwLearning.Domain.Ports.IRepository.IProgressionRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceProgression;
import org.springframework.stereotype.Service;

@Service
public class ServiceProgression implements IServiceProgression {

    private final IProgressionRepository progressionRepository;

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
        Progression progression = progressionRepository.trouverParId(eleveId, coursId);
        return progression.getTauxProgression();
    }
}
