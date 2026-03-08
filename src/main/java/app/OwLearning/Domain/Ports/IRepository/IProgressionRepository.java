package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Progression;

public interface IProgressionRepository {
    /**
     * Méthode qui va permettre de récupérer la progression d'un eleve sur un cours précis grace a leurs id
     * @param elevId id de l'élève
     * @param coursId id du cours
     * @return un objet progression
     */
    public Progression trouverParId(int elevId, int coursId);
}
