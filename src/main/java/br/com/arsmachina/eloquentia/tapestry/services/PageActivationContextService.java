package br.com.arsmachina.eloquentia.tapestry.services;

import org.apache.tapestry5.EventContext;

import br.com.arsmachina.eloquentia.entity.Page;

/**
 * Service that implements the {@link Page} -> page activation context and vice versa.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface PageActivationContextService {

	/**
	 * Returns the activation context for a given page.
	 * 
	 * @param page a {@link Page} instance.
	 * @return an {@link Object}.
	 */
	Object toActivationContext(Page page);

	/**
	 * Returns the activation context for a given URI.
	 * 
	 * @param page a {@link String}.
	 * @return an {@link Object}.
	 */
	Object toActivationContext(String uri);

	/**
	 * Returns the page corresponding to an {@link EventContext}.
	 * 
	 * @param eventContext an {@link EventContext}.
	 * @param incrementViews a <code>boolean</code> defining whether the number of views of the page
	 * should be incremented or not.
	 * @return a {@link Page} or <code>null</code>.
	 */
	Page toPage(EventContext eventContext, boolean incrementViews);
	
}
