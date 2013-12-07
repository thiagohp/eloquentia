package br.com.arsmachina.eloquentia.controller.impl;

import java.util.Date;
import java.util.List;

import br.com.arsmachina.controller.impl.ControllerImpl;
import br.com.arsmachina.eloquentia.controller.CommentController;
import br.com.arsmachina.eloquentia.dao.CommentDAO;
import br.com.arsmachina.eloquentia.entity.Comment;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.security.ObjectAction;

/**
 * Default {@link CommentController} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class CommentControllerImpl extends ControllerImpl<Comment, String> implements CommentController {

	final private CommentDAO dao;
	
	public CommentControllerImpl(CommentDAO dao) {
		super(dao);
		this.dao = dao;
	}
	
	@Override
	public List<Comment> findByPage(Page page) {
		return dao.findByPage(page);
	}

	public boolean isPermitted(User user, Comment comment, ObjectAction objectAction) {
		
		boolean permitted = false;
		
		switch (objectAction) {
			case DELETE:  
			case EDIT : permitted = false; break;
			case VIEW : permitted = true;
		}
		
		return permitted;
		
	}

	/***
	 * Sets the posted date and saves.
	 * @param comment a {@link Comment}.
	 */
	@Override
	public void save(Comment comment) {
		comment.setPosted(new Date());
		super.save(comment);
	}

}
