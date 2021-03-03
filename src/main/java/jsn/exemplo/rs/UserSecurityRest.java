package jsn.exemplo.rs;



import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jsn.exemplo.entidade.User;
import jsn.exemplo.security.JwtTokenUtil;
import jsn.exemplo.security.JwtUser;
import jsn.exemplo.security.UserRequest;
import jsn.exemplo.security.UserResponse;
import jsn.exemplo.service.UserService;


@RestController
@CrossOrigin
@RequestMapping(value = "/freedom_")
public class UserSecurityRest {
	
	
	
	@Autowired
	private UserService userService ;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUser userDetailsService;
	
	//  private static final User usuarioSetup = new User(Parametros.USERNAME_TESTE,Parametros.PASSWORD_TESTE );
    
		
	@RequestMapping 
	public User autenticarUsuario(@RequestBody User user) throws Exception {
		
		
		
		System.out.println("LOGIN " );
		
		return userService.autenticarUsuario(user);
	}
	
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new UserResponse(token));
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}