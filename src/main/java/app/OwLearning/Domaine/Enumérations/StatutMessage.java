package app.OwLearning.Domaine.Enumérations;

import lombok.Getter;

/**
 * Enum StatutMessage qui répértorie les différents status que peut avoir un message
 */
public enum StatutMessage
{
    ENVOI("Envoi"),
    ENVOYE("Envoyé");

    @Getter
    private final String label;

    /**
     * Constructeur de StatuMessage
     * @param label
     */
    private StatutMessage(String label)
    {
        this.label = label;
    }

}
