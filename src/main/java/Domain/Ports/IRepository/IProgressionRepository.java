package Domain.Ports.IRepository;

import Domain.Models.Progression;

import java.util.List;

public interface IProgressionRepository {
    /**
     * Méthode qui va permettre de récupérer la progression d'un eleve sur un cours précis grace a leurs id
     * @param elevId id de l'élève
     * @param coursId id du cours
     * @return un objet progression
     */
    public Progression trouverParId(int elevId, int coursId);
}
