package app.OwLearning.Infrastructure.Entités;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public class UtilisateurEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private int idUtilisateur;

    private String nom;
    private String prenom;
    private String pseudo;

    @Column(unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasseHash;

    @Column(name = "date_inscription")
    private Timestamp dateInscription;

    @Column(name = "derniere_activite")
    private Timestamp derniereActivite;

    public int getId()
    {
        return idUtilisateur;
    }
}
