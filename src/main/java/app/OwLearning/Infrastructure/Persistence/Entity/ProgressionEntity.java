package app.OwLearning.Infrastructure.Persistence.Entity;

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
@Table(name = "progression")
public class ProgressionEntity
{
    @EmbeddedId
    private ProgressionIdEntity id;

    @ManyToOne
    @MapsId("idCours")
    @JoinColumn(name = "id_cours")
    private CoursEntity cours;

    @ManyToOne
    @MapsId("idEleve")
    @JoinColumn(name = "id_eleve")
    private EleveEntity eleve;

    @Column(name = "taux_progression", nullable = false)
    private float tauxProgression = 0;
}
