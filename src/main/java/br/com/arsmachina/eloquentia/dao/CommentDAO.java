package br.com.arsmachina.eloquentia.dao;

import java.util.List;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.eloquentia.entity.Comment;
import br.com.arsmachina.eloquentia.entity.Page;

/**
 * DAO interface for {@link Comment}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface CommentDAO extends DAO<Comment, String> {
	
	/**
	 * Returns the comments of a given {@link Page}.
	 * 
	 * @param page a {@link Page}.
	 */
	List<Comment> findByPage(Page page);
	
}
