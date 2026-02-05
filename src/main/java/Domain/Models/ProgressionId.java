package Domain.Models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
/**
 * Classe ProgressionId qui représente la clé primaire de progression qui est la composition de la la clé primaire de Eleve et de Cours
 */
@Embeddable
public class ProgressionId implements Serializable
{
    private int idCours;
    private int idEleve;

    /**
     * Constructeur vide de ProgressionId
     */
    public ProgressionId()
    {}

    /**
     * Constructeur de ProgressionId
     * @param idCours
     * @param idEleve
     */
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
