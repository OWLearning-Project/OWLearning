package Application.Services;

import app.OwLearning.Domain.Models.Progression;
import Domain.Ports.IRepository.IProgressionRepository;
import Domain.Ports.IServices.IServiceProgression;

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
