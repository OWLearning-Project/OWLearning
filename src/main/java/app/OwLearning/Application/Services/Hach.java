package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Ports.IServices.IHach;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe hach qui permet de hacher un mot de passe et de valider si un mot de passe entré par l'utilisateur correspond à son hach.
 */
@Service
public class Hach implements IHach
{
    private final PasswordEncoder mdpEncoder;

    /**
     * Constructeur de hach
     * @param mdpEncoder
     */
    public Hach (PasswordEncoder mdpEncoder)
    {
        this.mdpEncoder = mdpEncoder;
    }

    /**
     * Méthode qui hach le mot de passe
     * @param motDePasse
     * @return le mot de passe haché
     */
    public String hacher(String motDePasse)
    {
        return mdpEncoder.encode(motDePasse);
    }

    /**
     * Méthode qui permet de vérifier si un mot de passe correspond bien à sa version hacher
     * @param motDePasse
     * @param motDePasseHache
     * @return true si ça correspond bien false sinon
     */
    public boolean valider(String motDePasse, String motDePasseHache)
    {
        return mdpEncoder.matches(motDePasse, motDePasseHache);
    }
}
