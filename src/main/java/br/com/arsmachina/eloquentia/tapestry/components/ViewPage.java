package br.com.arsmachina.eloquentia.tapestry.components;

import java.util.Locale;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.EloquentiaConstants;
import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;

/**
 * Component that views a {@link Page}. 
 * FIXME: add support for comments.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class ViewPage implements ClientElement {

	/**
	 * The page to be viewed. If not provided, the component will search for a page using the
	 * <code>uri</code> parameter.
	 */
	@Parameter(allowNull = false, required = true)
	private Page page;
	
	/**
	 * Defines whether just the title and teaser should be shown (
	 * <code>true</code>>) or the full content (<code>false</code>).
	 */
	@Parameter(allowNull = false, required = true)
	private boolean teaser;

	@Inject
	private Locale locale;
	
	@Inject
	private PageController pageController;
	
	@Inject
	private PageActivationContextService pageActivationContextService;
	
	@Inject
	private Messages messages;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isTeaser() {
		return teaser;
	}

	public void setTeaser(boolean teaser) {
		this.teaser = teaser;
	}
	
	public Object getContext() {
		return pageActivationContextService.toActivationContext(page);
	}

	public String getClientId() {
		return "page-" + page.getUri().replaceAll("/", "_");
	}
	
	public String getNumberOfPageViews() {
		return messages.format("eloquentia.number-of-page-views", page.getViews());
	}
	
	public boolean isEnableSyntaxHighlighter() {
		return page.getTags().contains(EloquentiaConstants.SYNTAX_HIGHLIGHT_TAG);
	}
	
}
