package br.com.arsmachina.eloquentia.dao;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * DAO interface for {@link Tag}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface TagDAO extends DAO<Tag, String> {
	
	/**
	 * Searches for an {@link Tag} with the given name and returns it if found.
	 * 
	 * @param name a {@link String}.
	 * @return an {@link Tag} or <code>null</code>.
	 */
	Tag findByName(String name);
	
}
