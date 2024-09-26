package com.example.hackingproject.common;

import com.example.hackingproject.login.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@SuppressWarnings("serial")
@Component
public class JwtTokenUtil {

    @Value("${jwt.signingkey}")

    private String SIGNING_KEY;

    private int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 10000;

    public String getUserIdFromToken(String token) {
        Claims claims =  getClaimFromToken(token);
        return claims.get("id").toString();
    }

    public String getUserAuthFromToken(String token) {
        Claims claims =  getClaimFromToken(token);
        return claims.get("access_level").toString();
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
        claims.put("id", user.getUSER_ID());
        claims.put("name", user.getUSER_NM());
        claims.put("access_level", user.getACCESS_LEVEL());
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

    public Boolean JWTTokenCheck(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String JWTToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SKJWTToken")) {
                    JWTToken = cookie.getValue();
                    break;
                }
            }
        }

        if(JWTToken==null||JWTToken==""){
            System.out.println("JWTTokenCheck - JWT 없음");
            return false;
        }else{
            if(validateToken(JWTToken)){
                // JWT 정상 인증
//            jwtTokenUtil.getUserIdFromToken(token);
//            jwtTokenUtil.validateTokenForUser(auth);
                System.out.println("JWTTokenCheck - JWT 정상 인증");
                return true;
            }else{
                // JWT 검증 실패
                return false;
            }
        }
    }




}
