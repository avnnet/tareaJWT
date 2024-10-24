package io.javabrains.springsecurityjwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;

public class JwtTokenUtils {
    
    // Clave secreta para firmar el token
    private final static SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tiempo de expiración del token en milisegundos (ejemplo: 1 hora)
    private final static long EXPIRATION_TIME = 3600000; 

    public static String generateToken(String email) {
        // Agregar cualquier otra información al token (claims)
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        // Generar el token JWT
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // El subject del token es el correo
                .setId(UUID.randomUUID().toString()) // ID único para el token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiración del token
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Firma con la clave secreta
                .compact();
    }
}
