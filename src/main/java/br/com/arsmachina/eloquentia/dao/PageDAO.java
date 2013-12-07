package br.com.arsmachina.eloquentia.dao;

import java.util.List;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * DAO interface for {@link Page}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface PageDAO extends DAO<Page, String> {

	/**
	 * Default page sorting criteria.
	 */
	public static final SortCriterion[] DEFAULT_SORT_CRITERIA = {new SortCriterion("posted", false)};

	/**
	 * Finds a {@link Page} by its URI. Same as <code>findByUri(uri, false)</code>.
	 * 
	 * @param uri a {@link String}.
	 * @return a {@link Page} or <code>null</code>.
	 */
	Page findByUri(String uri);

	/**
	 * Search by tag.
	 * 
	 * @param tag a {@link Tag}. If <code>null</code>, all tags will be considered.
	 * @param firstResult the first result to be shown. First index is 0.
	 * @param maxResults maximum number of results.
	 * @param sortCriteria sorting criteria.
	 * @return a {@link List} of {@link Page}.
	 */
	List<Page> findByTag(Tag tag, int firstResult, int maxResults, SortCriterion ... sortCriteria);

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

}
