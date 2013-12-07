package br.com.arsmachina.eloquentia.controller.impl;

import java.util.List;

import br.com.arsmachina.controller.impl.ControllerImpl;
import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.dao.UserDAO;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.enums.Role;
import br.com.arsmachina.eloquentia.security.PasswordHasher;

/**
 * Default {@link UserController} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class UserControllerImpl extends ControllerImpl<User, String> implements	UserController {

	final private UserDAO dao;
	
	final private PasswordHasher passwordHasher;
	
	public UserControllerImpl(UserDAO dao, PasswordHasher passwordHasher) {
		super(dao);
		this.dao = dao;
		this.passwordHasher = passwordHasher;
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return null;
	}

	public User findByLogin(String email) {
		return dao.findByLogin(email);
	}

	/**
	 * Hashes the password before saving plus adds the {@link Role#USER} and {@link Role#COMMENTER} 
	 * roles if the user has none.
	 */
	@Override
	public void save(User user) {
		user.setPassword(passwordHasher.hash(user.getPassword()));
		final List<Role> roles = user.getRoles();
		if (roles.isEmpty()) {
			roles.add(Role.USER);
			roles.add(Role.COMMENTER);
		}
		super.save(user);
	}

}
