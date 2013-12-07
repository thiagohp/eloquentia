package br.com.arsmachina.eloquentia.controller;

import java.util.List;

import br.com.arsmachina.controller.Controller;
import br.com.arsmachina.eloquentia.entity.Comment;
import br.com.arsmachina.eloquentia.entity.Page;

/**
 * DAO interface for {@link Comment}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface CommentController extends Controller<Comment, String> {

	/**
	 * Returns the comments of a given {@link Page}.
	 * 
	 * @param page a {@link Page}.
	 */
	List<Comment> findByPage(Page page);
	
}
