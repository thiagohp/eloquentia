package br.com.arsmachina.eloquentia.security;

/**
 * Interface that defines a service that hashes passwords.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 *
 */
public interface PasswordHasher {

	/**
	 * Returns the hash for a given password.
	 * 
	 * @param password a {@link String}.
	 * @return a the hashed password.
	 */
	public String hash(String password);
	
}
