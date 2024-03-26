package com.example.hackingproject.common;

import com.example.hackingproject.login.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@SuppressWarnings("serial")
@Component
public class JwtTokenUtil {

    @Value("${jwt.signingkey}")

    private String SIGNING_KEY;

    private int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 1000;

    public String getUserIdFromToken(String token) {
        Claims claims =  getClaimFromToken(token);
        return claims.get("id").toString();
    }

    public String getUserAuthFromToken(String token) {
        Claims claims =  getClaimFromToken(token);
        return claims.get("auth").toString();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Claims getClaimFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            return false;
        }
        return expiration.before(new Date());
    }


    public String generateTokenForUser(UserVO user) {
        return doGenerateTokenForUser(user,"user");
    }

    private String doGenerateTokenForUser(UserVO user, String subject) {

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("auth", user.getAuth());
        claims.put("type", "web");
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_SELLER")));

        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .compact();

    }

    /**
     * 토큰 유효성체크, 토큰값에 userid가 존재하는지 여부만 체크한다. 만료기한 x
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        final String userId = getUserIdFromToken(token);
//        final boolean isToken = !isTokenExpired(token);
        return !userId.isEmpty();
    }

    public Boolean validateTokenForUser(String token) {
    	final String userId = getUserIdFromToken(token);
    	return (!userId.isEmpty() && !isTokenExpired(token));
    }

}
