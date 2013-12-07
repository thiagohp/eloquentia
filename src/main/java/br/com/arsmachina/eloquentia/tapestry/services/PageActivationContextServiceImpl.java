package br.com.arsmachina.eloquentia.tapestry.services;

import java.util.Arrays;

import org.apache.tapestry5.EventContext;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;

/**
 * Default implementation of {@link PageActivationContextService}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class PageActivationContextServiceImpl implements PageActivationContextService {
	
	private static final String SEPARATOR = "/";
	
	final private PageController pageController;
	
	/**
	 * Single constructor of this class.
	 * 
	 * @param pageController a {@link PageController}.
	 */
	public PageActivationContextServiceImpl(PageController pageController) {
		super();
		this.pageController = pageController;
	}

	public Object toActivationContext(Page page) {
		Object activationContext = null;
		if (page != null) {
			activationContext = toActivationContext(page.getUri());
		}
		return activationContext;
	}

	public Object toActivationContext(final String uri) {
		Object activationContext = null;
		if (uri != null) {
			activationContext = Arrays.asList(uri.split(SEPARATOR));
		}
		return activationContext;
	}

	public Page toPage(EventContext eventContext) {
		
		Page page = null;
		final int count = eventContext.getCount();
		
		if (count > 0) {
			
			// optimize for most common case
			String uri = eventContext.get(String.class, 0);
			
			if (count > 1) {

				// join all the value to get the uri
				// uri has the first value of the context right now.
				StringBuilder builder = new StringBuilder(uri);
				for (int i = 1; i < count; i++) {
					builder.append(SEPARATOR);
					builder.append(eventContext.get(String.class, i));
				}
				
				uri = builder.toString();
				
			}
			
			page = pageController.findByUri(uri);
			
		}
		
		return page;
		
	}

}
