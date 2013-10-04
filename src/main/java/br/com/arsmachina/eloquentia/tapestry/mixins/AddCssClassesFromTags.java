package br.com.arsmachina.eloquentia.tapestry.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.services.EloquentiaConstants;

/**
 * Mixin that adds a list of tags in reverse order as CSS classes to the attached element,
 * which must implement {@link ClientElement}.
 * The reverse order is used so the first CSS classes take precedence by CSS overriding.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class AddCssClassesFromTags {
	
	/**
	 * Tags to be added as CSS classes.
	 */
	@Parameter(required = true, allowNull = false)
	private List<String> tags;
	
	/**
	 * Defines whether this mixin should really add the CSS tags or not.
	 */
	@Parameter("true")
	private boolean enable;
	
	@Inject
	@Symbol(EloquentiaConstants.TAG_CSS_CLASS_PREFIX_SYMBOL)
	private String prefix;
	
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
	
}
