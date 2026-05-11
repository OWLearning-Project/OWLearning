package app.OwLearning.Infrastructure.Persistence.Entity;

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
public class ProgressionIdEntity implements Serializable
{
    private int idCours;
    private int idEleve;

    public ProgressionIdEntity(int idCours, int idEleve)
    {
        this.idCours = idCours;
        this.idEleve = idEleve;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ProgressionIdEntity that = (ProgressionIdEntity) o;
        return idCours == that.idCours && idEleve == that.idEleve;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idCours, idEleve);
    }
}
