package app.OwLearning.Infrastructure.Persistence.Entity;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Difficulte;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cours")
public class CoursEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private int id;

    private String titre;
    private String description;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @Column(name = "est_prive")
    private boolean estPrive;

    @Column(name = "est_publie")
    private boolean estPublie;

    @ManyToOne
    @JoinColumn(name = "id_createur")
    private CreateurEntity createur;

    @ManyToMany
    @JoinTable(
            name = "inscription",
            joinColumns = @JoinColumn(name = "id_cours"),
            inverseJoinColumns = @JoinColumn(name = "id_eleve")
    )
    private List<EleveEntity> eleves;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulte")
    private Difficulte difficulte;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChapitreEntity> chapitres;

    @ElementCollection(targetClass = Categorie.class)
    @CollectionTable(name = "categorie_cours", joinColumns = @JoinColumn(name = "id_cours"))
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private List<Categorie> categories;

    public boolean getEstPrive()
    {
        return estPrive;
    }

    public boolean getEstPublie()
    {
        return estPublie;
    }
}
