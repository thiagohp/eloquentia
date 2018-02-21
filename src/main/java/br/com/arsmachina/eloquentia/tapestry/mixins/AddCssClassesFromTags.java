package br.com.arsmachina.eloquentia.tapestry.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.services.EloquentiaConstants;

/**
 * Mixin that adds a list of tags in reverse order as CSS classes to the attached element,
 * which must implement {@link ClientElement}.
 * The reverse order is used so the first CSS classes take precedence by CSS overriding.
 * In addition, this mixin also adds the tags' CSS itself as style HTML elements.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class AddCssClassesFromTags {
	
	final private static String ID_PREFIX = "style-tag-";
	
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
	
	@Inject
	@Symbol(EloquentiaConstants.TAG_CSS_CLASS_PREFIX_SYMBOL)
	private String prefix;
	
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
		
		List<String> reversed = new ArrayList<String>(tags.size());
		Tag tag;
		String css;
		String id;
		final Document document = writer.getDocument();
		final Element head = document.getRootElement().find("head");
		
		for (int i = tags.size() - 1; i >= 0; i--)  {
			reversed.add(tags.get(i));
		}
		
		for (String tagName : reversed) {
		
			id = ID_PREFIX + tagName;
			
			// don't add the style for the same tag twice
			if (document.getElementById(id) == null) {
				
				tag = tagController.findByName(tagName);
				css = tag.getCss();
				if (css != null && css.length() > 0) {
					head.element("style", "type", "text/css", "id", id).raw(css);
				}
				
			}
			
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
		
		element.forceAttributes("class", classAttribute);
		
	}
	
}
