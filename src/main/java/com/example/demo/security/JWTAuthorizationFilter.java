package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, "
                +"Access-Request-Method-Method,"
                +"Access-Request-Method-Headers,"
                +"Authorization,"
                +"File-Name,"
                +"Content-Disposition");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin ,"
               + "Access-Control-Allow-Credantials,Authorization,File-Name,filename, 'Content-Disposition'");
        String jwt=request.getHeader(SecurityConstants.HEADER_STRING);
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {if(jwt==null||!jwt.startsWith(SecurityConstants.TOKEN_PREFIX))
        {filterChain.doFilter(request,response); return;}
        Claims claims= Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX,""))
                .getBody();
        String username=claims.getSubject();
        ArrayList<Map<String,String>> roles= (ArrayList<Map<String, String>>) claims.get("roles");
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        roles.forEach(r-> {
            authorities.add(new SimpleGrantedAuthority(r.get("authority")));
        });

        UsernamePasswordAuthenticationToken authenticatedUser =
                new UsernamePasswordAuthenticationToken(username,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        filterChain.doFilter(request,response);
    }
    }
}
