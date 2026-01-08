package Domain.Models;

public class Createur extends Utilisateur{
    public Createur(){
        super();
    }
    public Createur(String nom, String prenom, String email, String motDePasseHash){
        super(nom, prenom, email, motDePasseHash);
    }

}
