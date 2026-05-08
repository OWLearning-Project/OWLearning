package app.OwLearning.Infrastructure.Persistence.Entity;

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
@Table(name = "createur")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class CreateurEntity extends UtilisateurEntity {
}