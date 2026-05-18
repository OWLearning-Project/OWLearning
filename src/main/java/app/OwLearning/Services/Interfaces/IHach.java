package app.OwLearning.Services.Interfaces;

/**
 * Interface IHach qui permet de hacher un mot de passe et de vérifier si le mot de passe correspond bien à son hach
 */
public interface IHach
{
    /**
     * Méthode qui permet de hacher un mot de passe
     * @param motDePasse
     * @return le mot de passe haché
     */
    public abstract String hacher(String motDePasse);

    /**
     * Méthode qui permet de vérfier la correspondance entre un mot de passe et son hach
     * @param motDePasse
     * @param motDePasseHache
     * @return true si correspondant false sinon
     */
    public abstract boolean valider(String motDePasse, String motDePasseHache);
}
