package com.AuthSystem.system.Security;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final long Expiration_time=15*60*1000; // 15 min
    private static long REFRESH_TOKEN_VALIDITY=24*60*60*1000;
    private final String SECRET="raqwfrawefwsegvrwbgdetgbaergqwdfewfeqgergetfrgetrhtgeadvg";

     public String generateToken(String email,boolean isaccesstoken){

         long expiration_time=isaccesstoken?Expiration_time:REFRESH_TOKEN_VALIDITY;
         String token=Jwts.builder()
                 .setSubject(email)
                 .issuedAt(new Date())
                 .expiration(new Date(System.currentTimeMillis() + expiration_time))
                 .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                 .compact();
   return token;
     }
     public String getemail(String token){
       String username =  Jwts.parser().setSigningKey(SECRET.getBytes())
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
       return username;
     }
     public boolean validateToken(String token){
         try {
             Jwts.parser().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token);
             return true;
         }catch (Exception e){
             e.printStackTrace();
             return false;
         }
     }
}
