package jsn.exemplo.util;



/**
 *
 * @author JSN
 * 
 * classe responsavel por ofuscar  e padronizar o fluxo de dados 
 */
public class Parametros {
	
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/login";
    public static final String USERNAME_TESTE = "admin";
	public static final String PASSWORD_TESTE = "12345" ;
	
	public static final String TAG_ACESSO_AUTORIZADO_TESTE = "ACESSO AUTORIZADO" ;
	public static final String TAG_ACESSO_NEGADO_TESTE = "ACESSO AUTORIZADO" ;
}
