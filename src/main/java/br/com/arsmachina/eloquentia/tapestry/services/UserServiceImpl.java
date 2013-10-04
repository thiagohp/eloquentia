package br.com.arsmachina.eloquentia.tapestry.services;

import org.apache.shiro.SecurityUtils;

import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * Default implementation of {@link UserService} based on Apache Shiro.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class UserServiceImpl implements UserService {
	
	final private UserController userController;
	
	/**
	 * Single constructor of this class. 
	 * @param userController an {@link UserController};
	 */
	public UserServiceImpl(UserController userController) {
		super();
		this.userController = userController;
	}

	public User getUser() {
		User user = null;
		if (isAuthenticated()) {
			final String login = (String) SecurityUtils.getSubject().getPrincipal();
			user = userController.findByLogin(login);
			if (user == null) {
				throw new RuntimeException(String.format("Logged user %s doesn't exist in the database", login));
			}
		}
		return user;
	}

	public boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
	public void logout() {
		SecurityUtils.getSubject().logout();
	}

}