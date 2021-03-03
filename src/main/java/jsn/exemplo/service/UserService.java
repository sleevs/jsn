package jsn.exemplo.service;

import org.springframework.stereotype.Service;

import jsn.exemplo.entidade.User;


/**
 *
 * @author JSN
 * 
 * classe responsavel pelos servico do modelo de dado User 
 */

@Service
public class UserService {
	
   
	private User usuario ;

//	@Autowired
//	private PasswordEncoder bcryptEncoder;

	
   public String userStatus() {
		
		
		StringBuilder retorno = new StringBuilder() ;
		retorno.append("JSNSOFTWARE - MICROSERVIÇO FUNCIONANDO ");
		
		return retorno.toString();
	}
   
   
	public User autenticarUsuario(User user) throws Exception {
		
		usuario = new User();
		
		try {
			
			if(user.getUsername().equals("admin") && user.getPassword().equals("12345") ){
				
				
				
				usuario.setUsername(user.getUsername());
				usuario.setTag("USUÁRIO COM ACESSO");
				StringBuilder retorno = new StringBuilder() ;
				
				
				return usuario;
			}
			
			usuario.setTag("USUÁRIO SEM ACESSO");
			
			return usuario ;
			
		}catch(Exception e) {
			throw new Exception("", e);
		}
	}
	
	
	

}
