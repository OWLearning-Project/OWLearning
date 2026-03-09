package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Progression;

/**
 * Interface IProgressionRepository définissant le contrat pour la récupération de la progression d'un cours
 */
public interface IProgressionRepository {
    /**
     * Méthode qui va permettre de récupérer la progression d'un élève sur un cours précis grâce à leurs id
     * @param elevId id de l'élève
     * @param coursId id du cours
     * @return un objet progression
     */
    public abstract Progression trouverParId(int elevId, int coursId);
}
