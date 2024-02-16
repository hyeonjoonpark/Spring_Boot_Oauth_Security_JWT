package spring.oauth.jwt.global.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

  // 만료되었는지 확인하는 메서드
  public static boolean isExpired(String token, String secretKey) {
    return Jwts.parser()
      .setSigningKey(secretKey)
      .parseClaimsJws(token)
      .getBody()
      .getExpiration()
      .before(new Date()); // token이 expired 된 것이 지금보다 전이면 expired 된것이다
  }

  public static String createJwt(String userName, String role, String secretKey, Long exprTime) {
    Claims claims = Jwts.claims();
    claims.put("userName", userName);
    claims.put("role", role);

    System.out.println("a");

    return Jwts
      .builder()
      .setClaims(claims)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + exprTime))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();

  }
}
