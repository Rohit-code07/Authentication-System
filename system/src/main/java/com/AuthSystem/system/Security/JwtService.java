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
    private final String SECRET="raqwfrawefwsegvrwbgdetgbaergqwdfewfeqgergetfrgetrhtgeadvg";

     public String generateToken(String username){

         String token=Jwts.builder()
                 .setSubject(username)
                 .issuedAt(new Date())
                 .expiration(new Date(System.currentTimeMillis() + Expiration_time))
                 .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                 .compact();
   return token;
     }
     public String getUsername(String token){
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
