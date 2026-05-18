package app.OwLearning.Infrastructure.ServicesExternes;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Services.Interfaces.IServiceToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe ServiceTokenJWT qui permet de générer un Token le valider, l'invalider et d'extraire l'ID
 */
@Service
public class ServiceTokenJWT implements IServiceToken
{

    private final String secretKeyString;
    private final long expirationTime;
    private final Key key;
    private final Set<String> tokenBlacklist;

    /**
     * Constructeur de ServiceTokenJWT
     * @param secretKeyString
     * @param expirationTime
     */
    public ServiceTokenJWT(@Value("${jwt.secret}") String secretKeyString,@Value("${jwt.expiration}") long expirationTime)
    {
        this.secretKeyString = secretKeyString;
        this.expirationTime = expirationTime;
        this.key = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        this.tokenBlacklist = Collections.newSetFromMap(new ConcurrentHashMap<>()); //Set qui gère les accès concurrents
    }

    /**
     * Méthode qui permet de générer un token avec dedans l'email, l'id, le pseudo, la date de création du token, la date de fin du token
     * @param utilisateur
     * @return le token généré
     */
    @Override
    public String genererToken(Utilisateur utilisateur)
    {
        long now = System.currentTimeMillis();

        String role = "utilisateur";

        if (utilisateur instanceof Eleve)
            role = "eleve";
        else if (utilisateur instanceof Createur)
            role = "createur";

        return Jwts.builder()
                .setSubject(utilisateur.getEmail()) // On stocke l'email comme identifiant
                .claim("id", utilisateur.getIdUtilisateur())   // On ajoute l'ID dans le token
                .claim("pseudo", utilisateur.getPseudo())
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Méthode qui permet d'invalider un token
     * @param token
     */
    @Override
    public void invaliderToken(String token)
    {
        if (token != null && !token.isEmpty())
        {
            tokenBlacklist.add(token);
        }
    }

    /**
     * Méthode qui permet d'extraire l'ID du token
     * @param token
     * @return l'id trouver
     */
    @Override
    public int extraireID(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // On récupère le claim "id" qu'on a mis dans genererToken
        return claims.get("id", Integer.class);
    }

    public String extraireEmail(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public String extraireRole(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }



    /**
     * Méthode qui permet de valider le token
     * @param token
     * @return true si token valide false sinon
     */
    public boolean validerToken(String token) {
        if (tokenBlacklist.contains(token))
        {
            return false;
        }
        try
        {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}
