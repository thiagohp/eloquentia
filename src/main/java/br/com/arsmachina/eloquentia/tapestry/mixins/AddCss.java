package br.com.arsmachina.eloquentia.tapestry.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import br.com.arsmachina.eloquentia.EloquentiaConstants;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.tapestry.pages.tag.Css;

/**
 * Mixin that adds a list of tags in reverse order as CSS classes to the attached element,
 * which must implement {@link ClientElement}.
 * The reverse order is used so the first CSS classes take precedence by CSS overriding.
 * In addition, this mixin also adds the first tag's CSS itself as a style HTML elements.
 * If the page parameter is not null and it's CSS is also not null, 
 * it's also added as an HTML style element.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class AddCss {
	
	/**
	 * Tags to be added as CSS classes.
	 */
	@Parameter(required = true, allowNull = false, cache = false)
	private List<String> tags;
	
	/**
	 * Defines whether this mixin should really add the CSS tags or not.
	 */
	@Parameter("true")
	private boolean enable;
	
	/** Page being rendered */
	@Parameter
	private Page page;
	
	@Inject
	@Symbol(EloquentiaConstants.TAG_CSS_CLASS_PREFIX_SYMBOL)
	private String prefix;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Inject
	private TagController tagController;
	
	private Element element;

	/**
	 * Stores the current element so we can add CSS class to it later in {@link #afterRenderBody(MarkupWriter)}.
	 * @param writer a {@link MarkupWriter}.
	 */
	void beforeRenderBody(MarkupWriter writer) {
		if (enable && tags.size() > 0) {
			element = writer.getElement();
		}
	}
	
	/**
	 * Adds the CSS classes.
	 * @param writer a {@link MarkupWriter}.
	 */
	void afterRenderBody(MarkupWriter writer)  {
		
		if (enable && tags.size() > 0) {
			addCssClasses();
			addCss(writer);
		}
		
	}

	private void addCss(MarkupWriter writer) {
		
		String tagName;
		Tag tag;
		
		for (int i = tags.size() - 1; i >= 0; i--)  {
			tagName = tags.get(i);
			tag = tagController.findByName(tagName);
			if (tag != null && tag.getCss() != null && tag.getCss().trim().length() > 0) {
				final Link link = pageRenderLinkSource.createPageRenderLinkWithContext(Css.class, tagName);
				javaScriptSupport.importStylesheet(new LinkAsset(link));
			}
		}

		if (page != null && page.getCss() != null && page.getCss().trim().length() > 0) {
			final Link link = pageRenderLinkSource.createPageRenderLinkWithContext(
					br.com.arsmachina.eloquentia.tapestry.pages.page.Css.class, page.getUri());
			javaScriptSupport.importStylesheet(new LinkAsset(link));
		}

	}

	private void addCssClasses() {
		
		String classAttribute = element.getAttribute("class");
		List<String> reversed = new ArrayList<String>(tags.size());
		
		for (int i = tags.size() - 1; i >= 0; i--)  {
			reversed.add(prefix + tags.get(i));
		}
		
		final String newCssClasses = Page.toString(reversed);
		
		if (classAttribute == null) {
			classAttribute = newCssClasses;
		}
		else {
			classAttribute = classAttribute.trim() + " " + newCssClasses; 
		}
		
		if (page != null) {
			classAttribute = classAttribute + " page-" + page.getUri();
		}
		
		element.forceAttributes("class", classAttribute);
		
	}

	/**
	 * An {@link Asset} implementation for {@link Link} instances.
	 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
	 */
	final private static class LinkAsset implements Asset {
		
		final private Link link;

		public LinkAsset(Link link) {
			this.link = link;
		}

		/**
		 * Returns <code>link.toAbsoluteURL()</code>.
		 */
		@Override
		public String toClientURL() {
			return link.toAbsoluteURI();
		}

		/**
		 * Returns <code>null</code>.
		 */
		@Override
		public Resource getResource() {
			return null;
		}
		
	}
	
}
