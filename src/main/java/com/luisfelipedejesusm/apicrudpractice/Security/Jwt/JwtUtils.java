package com.luisfelipedejesusm.apicrudpractice.Security.Jwt;

import com.luisfelipedejesusm.apicrudpractice.Services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * Token Secret and expiration time in milliseconds
     */
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Generates a Jwt token based on the current logged in user details
     * Uses HS512 signature algorithm
     * @param authentication authenticated user
     * @return Jwt Token
     */
    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Check if token is properly signed and valid
     * @param jwt Token
     * @return boolean
     */
    public boolean isJwtTokenValid(String jwt) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(jwt);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT Signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT Token: {}", e.getMessage());
        }catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("JWT Token is unsupported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("JWT Claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Get username from token body
     * @param jwt Token
     * @return username
     */
    public String getUsernameFromToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(jwt).getBody().getSubject();
    }
}
