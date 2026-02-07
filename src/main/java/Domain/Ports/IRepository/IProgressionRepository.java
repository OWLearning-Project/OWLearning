package Domain.Ports.IRepository;

import Domain.Models.Progression;

import java.util.List;

public interface IProgressionRepository {

    /**
    //Chercher toutes les progressions d'un élève et permets ensuite le traitement pour des progession précise
    public List<Progression> recupererToutesLesProgressionsParEleve(int eleveId);

    //Permets la mise a jour dans la base de données d'une progression
    public int maj(Progression uneProgression);
    **/
    //Permets de récuprer une progression d'un élève sur un cours précis
    public Progression trouverParId(int elevId, int coursId);
}
