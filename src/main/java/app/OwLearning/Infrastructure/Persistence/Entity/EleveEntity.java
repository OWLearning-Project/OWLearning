package app.OwLearning.Infrastructure.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "eleve")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class EleveEntity extends UtilisateurEntity
{
    private int age;

    @Column(name = "niveau_etude")
    private String niveauEtude;
}
