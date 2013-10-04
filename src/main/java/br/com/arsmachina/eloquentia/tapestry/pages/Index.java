package br.com.arsmachina.eloquentia.tapestry.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;

/**
 * Index page.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class Index {
	
	@Inject
	private PageActivationContextService pageActivationContextService;
	
	@Inject
	private TagController tagController;
	
	@Inject
	private MasterObjectProvider masterObjectProvider;
	
	private Page page;
	
	Object onActivate(EventContext context) {
		page = pageActivationContextService.toPage(context, true);
		return page != null || context.getCount() < 1 ? null : Index.class;
	}
	
	@Cached
	public Tag getTag() {
		Tag tag = null;
		if (page != null) {
			if (page.getTags().size() > 0) {
				tag = tagController.findByName(page.getTags().get(0));
			}
		}
		return tag != null ? tag : getMainTag();
	}

	@Cached
	public Tag getMainTag() {
		return tagController.getMainTag();
	}

	/**
	 * Defines the value of the <code>&lt;title&gt;</code> HTML tag.
	 * 
	 * @return a {@link String}.
	 */
	public String getTitle() {
		String title;
		final Tag mainTag = getMainTag();
		if (page != null) {
			final String firstTagName = page.getFirstTagName();
			Tag tag = null;
			if (firstTagName != null) {
				tag = tagController.findByName(firstTagName);
			}
			if (tag == null) {
				tag = mainTag;
			}
			String tagTitle = tag.getTitle();
			title = page.getTitle() + " - " + tagTitle;
			if (!tag.equals(mainTag)) {
				title += " - " + mainTag.getTitle();
			}
		}
		else {
			title = mainTag.getTitle() + " : " + mainTag.getSubtitle();
		}
		return title;
	}

	public Page getPage() {
		return page;
	}
	
}
