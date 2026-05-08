package app.OwLearning.Infrastructure.Persistence.Entity;

import app.OwLearning.Domain.Models.TypeRessource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ressource")
public class RessourceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ressource")
    private int idRessource;

    private String nom;
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_ressource")
    private TypeRessource type;
}
