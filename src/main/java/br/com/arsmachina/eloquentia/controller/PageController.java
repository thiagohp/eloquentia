package br.com.arsmachina.eloquentia.controller;

import java.util.List;

import br.com.arsmachina.controller.Controller;
import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.entity.User;
import br.com.arsmachina.eloquentia.security.ObjectAction;

/**
 * Controller interface for {@link User}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface PageController extends Controller<Page, String> {
	
	/**
	 * Finds a {@link Page} by its URI.
	 * 
	 * @param uri a {@link String}.
	 * @return a {@link Page} or <code>null</code>.
	 */
	Page findByUri(String uri);
	
	/**
	 * Finds a {@link Page} by its id, but first incrementing its {@link Page#getViews()} field
	 * if <code>incrementViews</code> is <code>true</code>.
	 *  
	 * @param id a {@link String} containing the page uri.
	 * @param incrementViews a <code>boolean</code> defining whether to increment the views 
	 * field of the returned page or not.
	 * @return an {@link Page} or null.
	 */
	Page findByUri(String uri, boolean incrementViews);

	/**
	 * Search by tag.
	 * 
	 * @param tag a {@link Tag}. If <code>null</code>, all tags will be considered.
	 * @param firstResult the first result to be shown. First index is 0.
	 * @param maxResults maximum number of results.
	 * @param sortCriteria an {@link SortCriterion} array.
	 * @return a {@link List} of {@link Page}.
	 */
	List<Page> findByTag(Tag tag, int firstResult, int maxResults, SortCriterion ... sortCriteria);
	
	/**
	 * Tells if a page with the given URI exists.
	 * 
	 * @param uri a {@link String} containing an URI.
	 * @return a boolean.
	 */
	boolean exists(String uri);

	/**
	 * Returns whether at least one page references a given tag name.
	 * 
	 * @param name a {@link String}.
	 * @return <code></code>
	 */
	boolean isTagReferenced(String name);
	
	/**
	 * Search by tag.
	 * 
	 * @param tag a tag name. If <code>null</code>, all tags will be considered.
	 * @param firstResult the first result to be shown. First index is 0.
	 * @param maxResults maximum number of results.
	 * @param sortCriteria sorting criteria.
	 * @return a {@link List} of {@link Page}.
	 */
	List<Page> findByTag(String tagName, int firstResult, int maxResults, SortCriterion ... sortCriteria);
	
	/**
	 * Tells whether the given user can perform the given action on the given page.
	 * @param user an {@link User}
	 * @param page a {@link Page}
	 * @param objectAction an {@link ObjectAction}
	 * @return a <code>boolean</code>
	 */
	boolean isPermitted(User user, Page page, ObjectAction objectAction);

}
