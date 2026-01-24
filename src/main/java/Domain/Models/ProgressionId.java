package Domain.Models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProgressionId implements Serializable
{
    private int idCours;
    private int idEleve;

    public ProgressionId()
    {}

    public ProgressionId(int idCours, int idEleve)
    {
        this.idCours = idCours;
        this.idEleve = idEleve;
    }

    @Override
    public boolean equals(Object o)
    {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProgressionId that = (ProgressionId) o;
    return idCours == that.idCours && idEleve == that.idEleve;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(idCours, idEleve);
    }

}
