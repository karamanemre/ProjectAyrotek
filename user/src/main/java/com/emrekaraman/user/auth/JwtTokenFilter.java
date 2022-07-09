package com.emrekaraman.user.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    public JwtTokenFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        /**
            ex: Bearer 1s45a45s4a1sda
         **/
        final String authHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.contains("Bearer")){
            token = authHeader.substring(7);
            try {
                email = tokenManager.getEmailByToken(token);
            }catch (Exception err){
                System.out.println(err);
            }
        }

        if (token != null && email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            if (tokenManager.tokenValidate(token)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                email,null,new ArrayList<>()
                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
