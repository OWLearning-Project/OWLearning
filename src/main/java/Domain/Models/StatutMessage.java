package Domain.Models;

public enum StatutMessage
{
    ENVOI("Envoi"),
    ENVOYE("Envoyé");

    private final String label;

    private StatutMessage(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
