package app.OwLearning.Domain.Models;

import jakarta.persistence.*;

@Entity
@Table(name="chapitres_termines")
public class ChapitreTermine
{
    @EmbeddedId
    private ChapitreTermineId chapitreTermineId ;

    @ManyToOne
    @MapsId("idChapitre")
    @JoinColumn(name = "id_chapitre")
    private Chapitre chapitre;

    @Column(name ="id_eleve",insertable = false,updatable = false)
    private int idEleve;

    public  ChapitreTermine()
    {}
    public ChapitreTermine(Chapitre chapitre, int idEleve)
    {
        this.chapitre = chapitre;
        this.idEleve = idEleve;
        this.chapitreTermineId = new ChapitreTermineId(chapitre.getId(), idEleve);
    }

    public ChapitreTermineId getChapitreTermineId()
    {
        return this.chapitreTermineId;
    }
    public Chapitre getChapitre()
    {
        return this.chapitre;
    }
    public int getIdEleve()
    {
        return this.idEleve;
    }
}
