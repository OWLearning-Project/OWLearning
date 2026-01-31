package Application.Services;

import Domain.Ports.IServices.IHach;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Hach implements IHach
{
    private final PasswordEncoder mdpEncoder;

    public Hach (PasswordEncoder mdpEncoder)
    {
        this.mdpEncoder = mdpEncoder;
    }

    public String hacher(String motDePasse)
    {
        return mdpEncoder.encode(motDePasse);
    }

    public boolean valider(String motDePasse, String motDePasseHache)
    {
        return mdpEncoder.matches(motDePasse, motDePasseHache);
    }
}
