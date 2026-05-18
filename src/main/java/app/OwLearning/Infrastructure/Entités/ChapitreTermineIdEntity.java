package app.OwLearning.Infrastructure.Entités;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ChapitreTermineIdEntity implements Serializable
{
    @Column(name = "id_chapitre")
    private int idChapitre;

    @Column(name = "id_eleve")
    private int idEleve;

    public ChapitreTermineIdEntity(int idChapitre, int idEleve)
    {
        this.idChapitre = idChapitre;
        this.idEleve = idEleve;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ChapitreTermineIdEntity that))
        {
            return false;
        }
        return idChapitre == that.idChapitre && idEleve == that.idEleve;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idChapitre, idEleve);
    }
}
