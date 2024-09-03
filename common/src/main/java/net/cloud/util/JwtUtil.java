package net.cloud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.cloud.model.LoginUser;
import org.springframework.validation.BindException;

import java.util.Date;
@Slf4j
public class JwtUtil {
    private final static long EXPIRE = 1000*60*60*24*7;
    private final static String SECRET = "Rongyi666.net";
    private final static String TOKEN_PREFIX = "Rongyi06";
    private final static String SUBJECT="Rongyi";
    public static String generateJsonWebToken(LoginUser loginUser){
        if(loginUser==null){
            throw new NullPointerException("Object is empty");
        }
        long userId = loginUser.getId();
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", loginUser.getHeadImg())
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .claim("ip_addr", loginUser.getIpAddr())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        token = TOKEN_PREFIX + token;
        return token;
    }

    public static Claims verifyJwtWebToken(String token){
        try {
            Claims body = (Claims)Jwts.parser()
                    .setSigningKey(SECRET)
                    .parse(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            return body;
        }catch (Exception e){
            log.info("Jwt token verify failed");
            return null;
        }
    }
}
