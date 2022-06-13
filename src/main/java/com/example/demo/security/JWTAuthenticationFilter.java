package com.example.demo.security;

import com.example.demo.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)throws AuthenticationException
    {
        User user=null;
        try
        {user=new ObjectMapper().readValue(request.getInputStream(),User.class);}
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException
    {
        org.springframework.security.core.userdetails.User spring_user= (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String jwt= Jwts.builder()
                .setSubject(spring_user.getUsername())
                .setExpiration(new Date(currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .claim("roles",spring_user.getAuthorities())
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwt);


    }
}
