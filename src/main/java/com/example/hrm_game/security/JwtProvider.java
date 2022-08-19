package com.example.hrm_game.security;

import com.example.hrm_game.security.data.JWTBlackList;
import com.example.hrm_game.security.repository.JWTBlackListRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

//    @Value("$(jwt.secret)")
    private String jwtSecret = "jwtsecret";
    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    public String generateToken(String login, HttpServletRequest httpServletRequest) {
        Date date = Date.from(LocalDate.now()
                .plusDays(15)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, httpServletRequest.getRemoteAddr())
                .compact();
    }

    public boolean validateToken(String token, ServletRequest servletRequest) {
        try {
            JWTBlackList tokenInBlackList =
                    searchTokenInBlackList(token);
            if(tokenInBlackList != null){
                log.error("Token in blacklist");
                return false;
            }
            Jwts.parser().setSigningKey(servletRequest.getRemoteAddr()).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token, ServletRequest servletRequest) {
        Claims claims = Jwts.parser().setSigningKey(servletRequest.getRemoteAddr()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private JWTBlackList searchTokenInBlackList(String token){
        return jwtBlackListRepository.findJWTBlackListByToken(token);
    }
}
