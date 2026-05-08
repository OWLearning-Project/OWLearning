package app.OwLearning.Infrastructure.Persistence.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discussion")
public class DiscussionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discussion")
    private int idDiscussion;

    @ManyToMany
    @JoinTable(
            name = "participation_discussion",
            joinColumns = @JoinColumn(name = "id_discussion"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur")
    )
    private List<UtilisateurEntity> participants;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageEntity> messages;

    public int getId()
    {
        return idDiscussion;
    }
}
