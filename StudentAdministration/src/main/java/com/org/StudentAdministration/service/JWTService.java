package com.org.StudentAdministration.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.websocket.Decoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

   private String seceretkey="";

   /* It is used to generate a secrete key dynamically every time application starts */

   public JWTService(){
       try {
           KeyGenerator keygen= KeyGenerator.getInstance("HmacSHA256");
           SecretKey skey= keygen.generateKey();
           seceretkey=Base64.getEncoder().encodeToString(skey.getEncoded());
       } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
       }
   }


 /* This method is used to generate a JWT token using given particular name this token is later used for authentication
 * and authorization  */


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 1 hour in milliseconds
                .and()
                .signWith(getKey())
                .compact();
    }

   /*  It converts a Base64-encoded secret key into a format that the JWT library (jjwt) can use for signing */

    private SecretKey getKey() {
        byte[] ks= Decoders.BASE64.decode(seceretkey);
        return Keys.hmacShaKeyFor(ks);
    }

    /* Here extracting the user information from jwt token  */

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* Here validating the token by checking username is matching with the userDetails.getUsername()
    * and by checking expiration  */

    public boolean validateToken(String token, UserDetails userDetails) {
            String username = extractUserName(token);
            Claims claims = extractAllClaims(token);
            return (username.equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date()));

    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
