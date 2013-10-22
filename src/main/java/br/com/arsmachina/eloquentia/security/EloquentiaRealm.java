package br.com.arsmachina.eloquentia.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.slf4j.Logger;

import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.enums.Role;

/**
 * Eloquentia's {@link AuthorizingRealm} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class EloquentiaRealm extends AuthorizingRealm {
	
	private static final AllPermission ALL_PERMISSION = new AllPermission();

	final private UserController userController;
	
	final private PasswordService passwordService;
	
	final private ObjectPermissionChecker<?> objectPermissionChecker;
	
	final private Logger logger;
	
	/**
	 * Single constructor of this class.
	 * 
	 * @param userController an {@link UserController}.
	 * @param passwordService a {@link PasswordService}.
	 * @param permissionProvider a {@link PermissionProvider}.
	 * @param logger a {@link Logger}.
	 */
	public EloquentiaRealm(UserController userController, PasswordService passwordService,
			@Primary ObjectPermissionChecker<?> objectPermissionChecker, Logger logger) {
		
		assert userController != null;
		assert passwordService != null;
		assert objectPermissionChecker != null;
		assert logger != null;
		
		this.userController = userController;
		this.passwordService = passwordService;
		this.objectPermissionChecker = objectPermissionChecker;
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
				
				final String realmName = getName();
				final SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(username, realmName);
				principalCollection.add(user, realmName);
				authenticationInfo = new SimpleAuthenticationInfo(principalCollection, usernamePasswordToken.getPassword());
				
			}
			
		}
		
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		final User user = principals.byType(User.class).iterator().next();
		final List<Role> roles = user.getRoles();
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		
		for (Role role : roles) {
			sai.addRole(role.name());
		}
		
		if (roles.contains(Role.ADMIN)) {
			sai.addObjectPermission(ALL_PERMISSION);
		}
		else {
			sai.addObjectPermission(new ObjectPermission(principals.oneByType(User.class), objectPermissionChecker));
		}
		
		return sai;
		
	}

}
