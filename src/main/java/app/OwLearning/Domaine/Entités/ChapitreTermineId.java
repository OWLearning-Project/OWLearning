package app.OwLearning.Domaine.Entités;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
public class ChapitreTermineId
{

    private int idChapitre;

    private int idEleve;

    public ChapitreTermineId() {}

    public ChapitreTermineId(int idChapitre, int idEleve)
    {
        this.idChapitre = idChapitre;
        this.idEleve = idEleve;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ChapitreTermineId)) return false;
        ChapitreTermineId that = (ChapitreTermineId) o;
        return idChapitre == that.idChapitre && idEleve == that.idEleve;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idChapitre, idEleve);
    }
}
