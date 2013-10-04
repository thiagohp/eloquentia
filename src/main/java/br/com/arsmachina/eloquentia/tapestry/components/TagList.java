package br.com.arsmachina.eloquentia.tapestry.components;

import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.tapestry.pages.tag.Index;

/**
 * Component that generates a tag list.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagList {

	/**
	 * Tags to be shown.
	 */
	@Parameter(allowNull = false, required = true)
	private List<String> tags;
	
	@Inject
	private TagController tagController;
	
	@Inject
	private Messages messages;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	/**
	 * Renders the tags.
	 * 
	 * @param writer a {@link MarkupWriter}.
	 * @return <code>false</code> so the component body isn't rendered.
	 */
	boolean beginRender(MarkupWriter writer) {
		
		Tag tag;
		String tagTitle;
		writer.element("ol", "class", "tag-list");

		// TagController.findByName() will be heavily cached, so we can do this loop this way,
		// instead of trying to load all tags from the database at once.
		for (String tagName : tags) {
			
			writer.element("li");
			
			tag = tagController.findByName(tagName);
			tagTitle = tag != null ? tag.getTitle() : tagName;
			
			final Link link = pageRenderLinkSource.createPageRenderLinkWithContext(Index.class, tagName);
			final Element a = writer.element("a", "href", link.toAbsoluteURI(), "class", "tag-link");
			a.text(tagName);
			if (!tagName.equals(tagTitle)) {
				a.attribute("title", tagTitle);
			}
			
			writer.end(); // li
			writer.end(); // a
			
		}
		
		writer.end(); // ol
		
		return false;
		
	}
	
}
