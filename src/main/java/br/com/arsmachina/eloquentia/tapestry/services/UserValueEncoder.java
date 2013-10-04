package br.com.arsmachina.eloquentia.tapestry.services;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.entity.User;

public class UserValueEncoder implements ValueEncoder<User>, ValueEncoderFactory<User> {
	
	final private UserController userController;

	/**
	 * Single constructor of this class.
	 * 
	 * @param pageController a {@link PageController}.
	 */
	public UserValueEncoder(UserController userController) {
		this.userController = userController;
	}

	public ValueEncoder<User> create(Class<User> type) {
		return this;
	}

	public String toClient(User user) {
		return user != null ? user.getLogin() : null;
	}

	public User toValue(String clientValue) {
		User user = null;
		if (clientValue != null) {
			user = userController.findByLogin(clientValue);
		}
		return user;
	}

}
