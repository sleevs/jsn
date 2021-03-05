package jsn.exemplo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jsn.exemplo.entidade.User;
import jsn.exemplo.util.Parametros;

public class UserAuthentication extends AbstractAuthenticationProcessingFilter   {

	
	
	 private AuthenticationManager authenticationManager;
	 
	 
	public UserAuthentication(String url , AuthenticationManager authenticationManager) {
	
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authenticationManager);
		
	}


	
	  
	   @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	    
		   User usuarioSetup = new User();
	
		   usuarioSetup.setPassword(Parametros.PASSWORD_TESTE);
		   usuarioSetup.setUsername(Parametros.USERNAME_TESTE);
		   try {
	            User usuario = new ObjectMapper()
	                    .readValue(req.getInputStream(), User.class);

	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                    		usuario.getUsername(),
	                    		usuario.getPassword(),
	                    		new ArrayList<>())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	  

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException {
	        String token = JWT.create()
	                .withSubject(((User) auth.getPrincipal()).getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + Parametros.EXPIRATION_TIME))
	                .sign(Algorithm.HMAC512(Parametros.SECRET.getBytes()));

	        String body = ((User) auth.getPrincipal()).getUsername() + " " + token;

	        res.getWriter().write(body);
	        res.getWriter().flush();
	    }
	  
	  
}
