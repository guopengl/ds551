package edu.guopengl.utils;

import edu.guopengl.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    //time in ms
    private static final long EXPIRE = 60000 * 60; //1min

    /**
     * 加密秘钥
     */
    private static final String SECRET = "lgp1269498026";

    public static String generateJWT(User user){
        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
    }

    public static Claims checkJWT(String token){
        try{
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }


}
