package Shared.Exceptions;

public class ExceptionMauvaisIdentifiants extends RuntimeException {

    private String email;

    public ExceptionMauvaisIdentifiants(String message, String email) {
        super(message);
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() + " Tentative de connexion échouée pour l'email : " + this.email;
    }
}