package app.OwLearning.Domain.Models;

import jakarta.persistence.Column;

import java.util.Objects;

public class ChapitreTermineId
{

    @Column(name = "id_chapitre")
    private int idChapitre;

    @Column(name = "id_eleve")
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
