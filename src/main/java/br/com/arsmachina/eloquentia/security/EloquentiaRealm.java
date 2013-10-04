package br.com.arsmachina.eloquentia.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.slf4j.Logger;

import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * Eloquentia's {@link AuthenticatingRealm} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class EloquentiaRealm extends AuthenticatingRealm {
	
	final private UserController userController;
	
	final private PasswordService passwordService;
	
	final private Logger logger;

	/**
	 * Single constructor of this class.
	 * 
	 * @param userController an {@link UserController}.
	 * @param passwordService a {@link PasswordService}.
	 * @param logger a {@link Logger}.
	 */
	public EloquentiaRealm(UserController userController, PasswordService passwordService,
			Logger logger) {
		this.userController = userController;
		this.passwordService = passwordService;
		this.logger = logger;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo authenticationInfo = null;
		if (token instanceof UsernamePasswordToken) {
			UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
			String username = usernamePasswordToken.getUsername();
			User user = null;
			try {
				user = userController.findByLogin(username);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception while calling UserController.findByLogin()", e);
				throw new RuntimeException(e);
			}
			if (user != null && passwordService.passwordsMatch(usernamePasswordToken.getPassword(), user.getPassword())) {
				authenticationInfo = new SimpleAuthenticationInfo(username, usernamePasswordToken.getPassword(), getName());
			}
		}
		return authenticationInfo;
	}

}
