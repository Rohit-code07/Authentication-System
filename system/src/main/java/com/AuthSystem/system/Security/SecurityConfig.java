package com.AuthSystem.system.Security;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

private  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
private JwtAuthenticationFilter jwtAuthenticationFilter;

 public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
     this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
     this.jwtAuthenticationFilter = jwtAuthenticationFilter;
 }
 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http.csrf(csrf -> csrf.disable());
     http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.
             requestMatchers(HttpMethod.POST,"/login","/register").permitAll().
             anyRequest()
             .authenticated());
     http.addFilterBefore((Filter) jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
     return http.build();

 }

}

