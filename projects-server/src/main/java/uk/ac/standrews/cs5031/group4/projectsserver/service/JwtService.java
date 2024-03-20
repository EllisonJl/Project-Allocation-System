package uk.ac.standrews.cs5031.group4.projectsserver.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    /**
     * Secret key used for signing the JWT.
     */
    private static final String SECRET = "27c489704e17311e32b32fb915c42f5adade4a51ed639e9aee727529a93e68659a78a40d61b62b68b1c37948140141dffaef67de0ee8bece4cb44cbcfa52e920";

    /**
     * The length of time a token will be valid for, in milliseconds.
     */
    private static final long VALIDITY_LENGTH_MS = 1000 * 60 * 60;

    /**
     * Extracts the username from the given JWT.
     *
     * @param token The JWT to decode.
     * @return The username contained in the JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the given JWT.
     *
     * @param token The JWT to decode.
     * @return The expiration date of the given JWT.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic function for extracting a claim from the given JWT.
     *
     * @param token          The JWT to decode.
     * @param claimsResolver Function to use for extracting the claim.
     * @return The extracted claim from the JWT.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Checks that the given JWT is valid, and has not expired.
     *
     * @param token       The JWT to validate.
     * @param userDetails The details of the user to validate against.
     * @return Whether the JWT is valid.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Generates a JWT for the given user.
     *
     * @param username The username to generate a token for.
     * @return The generated token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Extracts all claims from the given JWT.
     *
     * @param token The token to parse.
     * @return All claims in the JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param token The JWT token to check.
     * @return Whether the token has expired or not.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Creates a new JWT for the given username with the given set of claims.
     *
     * @param claims   The claims to encode in the JWT.
     * @param username The username to issue the JWT for.
     * @return The generated token.
     */
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_LENGTH_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Returns the secret key used for signing generated tokens.
     *
     * @return This application's secret key.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
