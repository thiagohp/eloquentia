package br.com.arsmachina.eloquentia.controller.impl;

import java.util.Date;
import java.util.List;

import br.com.arsmachina.controller.impl.ControllerImpl;
import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.dao.PageDAO;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.security.ObjectAction;

/**
 * Default {@link PageController} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class PageControllerImpl extends ControllerImpl<Page, String> implements	PageController {

	final private PageDAO dao;
	
	public PageControllerImpl(PageDAO dao) {
		super(dao);
		this.dao = dao;
	}

	public Page findByUri(String uri) {
		return dao.findByUri(uri);
	}

	public Page findByUri(String uri, boolean incrementViews) {
		return dao.findByUri(uri, incrementViews);
	}

	public List<Page> findByTag(Tag tag, int firstResult, int maxResults,
			SortCriterion... sortCriteria) {
		if (sortCriteria == null || sortCriteria.length == 0) {
			sortCriteria = PageDAO.DEFAULT_SORT_CRITERIA;
		}
		return dao.findByTag(tag, firstResult, maxResults, sortCriteria);
	}

	/**
	 * Updates the page in the database, first updating the edited field.
	 * 
	 * @param page a {@link Page}.
	 */
	@Override
	public Page update(Page page) {
		page.setEdited(new Date());
		return super.update(page);
	}

	public boolean exists(String uri) {
		return dao.exists(uri);
	}

	public boolean isTagReferenced(String name) {
		return dao.isTagReferenced(name);
	}

	public List<Page> findByTag(String tagName, int firstResult,
			int maxResults, SortCriterion... sortCriteria) {
		return dao.findByTag(tagName, firstResult, maxResults, sortCriteria);
	}

	@Override
	public boolean isPermitted(User user, Page page, ObjectAction objectAction) {
		
		boolean permitted = false;
		boolean isAuthor = page.getPostedBy().equals(user);
		
		switch (objectAction) {
			case DELETE:  
			case EDIT : permitted = isAuthor; break;
			case VIEW : permitted = true;
		}
		
		return permitted;
		
	}
	
}
