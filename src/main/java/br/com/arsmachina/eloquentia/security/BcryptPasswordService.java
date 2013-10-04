package br.com.arsmachina.eloquentia.security;

import org.apache.shiro.authc.credential.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This implementation was copied from a <a href="">patch provided by Terry
 * Chia</a>. in issue SHIRO-290.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class BcryptPasswordService implements PasswordService, PasswordHasher {
	
	public String encryptPassword(Object plaintextPassword)
			throws IllegalArgumentException {
		return BCrypt.hashpw(plaintextPassword.toString(), BCrypt.gensalt(10));
	}

	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
		return BCrypt.checkpw(toString(submittedPlaintext), encrypted);
	}

	private String toString(Object o) {
		if (o == null) {
			String msg = "Argument for String conversion cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		if (o instanceof byte[]) {
			return toString((byte[]) o);
		} else if (o instanceof char[]) {
			return new String((char[]) o);
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return o.toString();
		}
	}

	public String hash(String password) {
		return encryptPassword(password);
	}
}
