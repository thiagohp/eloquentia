package br.com.arsmachina.eloquentia.dao;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.eloquentia.entity.File;

/**
 * DAO interface for {@link File}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface FileDAO extends DAO<File, String> {
	
	/**
	 * Searches for an {@link File} with the given name and returns it if found.
	 * 
	 * @param name a {@link String}.
	 * @return an {@link File} or <code>null</code>.
	 */
	File findByName(String name);
	
}
