package br.com.arsmachina.eloquentia.tapestry.services;

import br.com.arsmachina.eloquentia.entity.User;

/**
 * Service that provides some utility methods for dealing with services in a web environment.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface UserService {

	/**
	 * Returns the authenticated user or <code>null</code> if the user isn't authenticated.
	 * 
	 * @return an {@link User} or <code>null</code>.
	 */
	User getUser();
	
	/**
	 * Tells whether the current user is authenticated.
	 * 
	 * @return <code>true</code> or <code>false</code>;
	 */
	boolean isAuthenticated();
	
	/**
	 * Logs out the curret user.
	 */
	void logout();
	
}
