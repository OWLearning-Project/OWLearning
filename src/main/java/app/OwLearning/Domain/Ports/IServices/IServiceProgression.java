package app.OwLearning.Domain.Ports.IServices;

/**
 * Interface IServiceProgression définissant le contrat pour le traitement de la progression
 */
public interface IServiceProgression {
    /**
     * Méthode qui permet de récupérer le taux de progression d'un élève sur un cours
     * @param eleveId id de l'élève
     * @param coursId id du cours
     * @return un float qui correspond au taux de progression
     */
    public abstract float getProgressionEleve(int eleveId, int coursId);
}
