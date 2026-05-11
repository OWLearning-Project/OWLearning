package app.OwLearning.Domain.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapitreTermine
{
    private ChapitreTermineId chapitreTermineId ;

    private Chapitre chapitre;

    private int idEleve;

    public  ChapitreTermine()
    {}
    public ChapitreTermine(Chapitre chapitre, int idEleve)
    {
        this.chapitre = chapitre;
        this.idEleve = idEleve;
        this.chapitreTermineId = new ChapitreTermineId(chapitre.getId(), idEleve);
    }

}
