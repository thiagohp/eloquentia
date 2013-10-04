package br.com.arsmachina.eloquentia.controller;

import br.com.arsmachina.controller.Controller;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * Controller interface for {@link Tag}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface TagController extends Controller<Tag, String> {
	
	/**
	 * Finds a {@link Tag} by its name.
	 * 
	 * @param uri a {@link String}.
	 * @return a {@link Tag} or <code>null</code>.
	 */
	Tag findByName(String name);
	
	/**
	 * Returns the main tag.
	 * 
	 * @return a {@link Tag}.
	 * @see Tag#MAIN_TAG_NAME.
	 */
	Tag getMainTag();
	
	/**
	 * Tells whether a tag with a given name exists and is a subdomain.
	 * 
	 * @param tagName a {@link String} with the tag name.
	 * @return a <code>boolean</code>.
	 * @see Tag#isSubdomain().
	 */
	boolean isSubdomain(String tagName);
	
}
