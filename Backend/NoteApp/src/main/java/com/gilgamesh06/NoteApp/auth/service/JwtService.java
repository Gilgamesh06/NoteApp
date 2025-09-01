package com.gilgamesh06.NoteApp.auth.service;

import com.gilgamesh06.NoteApp.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Clase que genera los metodos para {crear,validar,extraerInformacion} de los tokens JWT
 */
@Service
public class JwtService {

    /**
     * Clave secreta que se importa desde application.properties
     */
    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Metodo que me genera un objeto de la clase Key a partir del atributo SECRET_KEY
     * @see Key mirar clase key
     * @return retorna un objeto de la clase Key
     */
    private Key getSignKey() {
        // convierte el atributo SECRET_KEY que se espera que este en BASE64 y lo convierte en un arreglo bytes
        // cree un objeto de tipo Key a partir del arreglo de bytes
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    /**
     * Metodo para generar el token que recibe el parametro userDetails
     * @see Jwts mirar la clase jwts
     * @param userDetails contiene el nickname y la password del usuario (encriptada)
     * @return retorna un String que contiene el token de acceso duracion 1 hora
     */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder() // construye un jwt
                .setSubject(userDetails.getUsername()) // define el sujeto con el nickname del usuario
                .setIssuedAt(new Date()) // fecha de expedicion del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(getSignKey(), SignatureAlgorithm.HS256)  // firmar token con la key y se encripta con HS256
                .compact(); // compactar en un String
    }

    /**
     * Metodo para extrear  el cuerpo del jwt y retornarlo como un Claims
     * @param token es el token generado por el metodo generateToken es un string
     * @return retorna un claim que contiene el cuerpo
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // crea un nuevo constructor para el parse
                .setSigningKey(getSignKey()) // asigna la clave para poder verificar el token
                .build() // consturye el objeto
                .parseClaimsJws(token)   // verifica el token
                .getBody(); // obtiene el cuerpo del jwt y lo convierte en Claims
    }

    /**
     * Metodo para extraer el nombre del usuario (nickname) del cuerpo del JWT a traves del metodo extractAllClaims
     * @param token es el token jwt generado que se necesita para obtener su cuerpo y nombre de usuario (nickname)
     * @return retorna un String que contiene el nickname
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Metodo que valida si un token esta expirado
     * @param token es el jwt generado
     * @return un boolean: true si la fecha de expiracion del token es antes de la fecha actual (sistema)
     * si no retorna flase
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Metodo para validar el token
     * @param token es el jwt generado
     * @param userDetails es un objeto que contiene el nickname y password del usuario
     * @return un boolean: true si el usuario del cuerpo del token es igual al de l userDetails y el token no esta expirado
     * en caso contrario retorna un false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
