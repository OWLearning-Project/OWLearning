package app.OwLearning.Infrastructure.Entités;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chapitre")
public class ChapitreEntity
{
    @Id
    @Column(name = "id_chapitre")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private String titre;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cours")
    private CoursEntity cours;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "ressource_chapitre",
            joinColumns = @JoinColumn(name = "id_chapitre"),
            inverseJoinColumns = @JoinColumn(name = "id_ressource")
    )
    private List<RessourceEntity> ressources;
}
