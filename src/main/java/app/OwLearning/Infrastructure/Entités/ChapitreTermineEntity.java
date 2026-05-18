package app.OwLearning.Infrastructure.Entités;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chapitres_termines")
public class ChapitreTermineEntity
{
    @EmbeddedId
    private ChapitreTermineIdEntity chapitreTermineId;

    @ManyToOne
    @MapsId("idChapitre")
    @JoinColumn(name = "id_chapitre")
    private ChapitreEntity chapitre;

    @Column(name = "id_eleve", insertable = false, updatable = false)
    private int idEleve;
}
