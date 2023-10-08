package com.shop.ShopApplication.config;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "kWwdlIDrY89fT9W/n+WLYwMlVkcv4RLWniZgRyoo4CLpJfDeq4BmZkUp3fpcYuxOwW16ixXLKk1Wuz0GNvdDL9cJVHrDtxBwjMdJgtt6Gfs5wc7MiJVxVmcZ1jjPPzYVmSubAhwsCaq4n+g3rLBzLpWvWMRTQYCEyAMlPeC3hQ48jx9iUg7kTdjsopqHIvfdgXezdT8Yfu1yqOXmIcnR3WMNbZLCvqw51Gucvy+QpFDE+SR9d6978I1gLdXcdxJntzX2KCkQlPiYxz2jXUtczz40sr6/03DEQqQuNuoZVFnqt0d8bhomS3a0yBEq2pYpcTsW9G/1VZ6zqMlw41E/NuQ6bhGtBXRoQxNm9keaElM=\n";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 100))
                .signWith(getSignIngKey(), SignatureAlgorithm.HS256)

                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        final Claims claims = extractAllClaims(token);
        final String username = extractUsername(token);

        // Extract the expiration time from the token
        Date expiration = claims.getExpiration();

        // Set the allowed clock skew (in milliseconds)
        long allowedClockSkewMillis = 300000; // 5 minutes

        // Calculate the current time with allowed clock skew
        Date currentTimeWithSkew = new Date(System.currentTimeMillis() + allowedClockSkewMillis);

        // Check if the token is expired, considering allowed clock skew
        return (username.equals(userDetails.getUsername())) && (expiration.after(currentTimeWithSkew));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignIngKey())
                .build().parseClaimsJws(token).getBody();
    }

    private SecretKey getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
