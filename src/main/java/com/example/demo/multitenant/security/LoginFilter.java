package com.example.demo.multitenant.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        System.out.printf("Authentication manager: {} \n", authManager);
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException, IOException, ServletException {

      AccountCredentials creds = new ObjectMapper().
        readValue(req.getInputStream(), AccountCredentials.class);

      System.out.println(creds.getUsername() + ">> UserName");
      System.out.println(creds.getPassword() + ">> Password");
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getUsername(),
              creds.getPassword(), Collections.emptyList());

        System.out.println(token);

        AuthenticationManager authenticationManager = getAuthenticationManager();

        if(authenticationManager != null) {
            return getAuthenticationManager().authenticate(token );
        } else {
            System.out.println("Authentication manager value is null");
            return null;
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {

      Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
      String tenant = "";
      for (GrantedAuthority gauth : authorities) {
          tenant = gauth.getAuthority();
      }

      AuthenticationService.addToken(res, auth.getName(), tenant.substring(5));
    }
}
