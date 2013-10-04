package br.com.arsmachina.eloquentia.dao;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * DAO interface for {@link User}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface UserDAO extends DAO<User, String> {
	
	/**
	 * Searches for an {@link User} with the given username returns it if found.
	 * 
	 * @param login a {@link String}.
	 * @return an {@link User} or <code>null</code>.
	 */
	User findByLogin(String login);
	
}
