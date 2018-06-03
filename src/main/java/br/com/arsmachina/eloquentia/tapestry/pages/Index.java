package br.com.arsmachina.eloquentia.tapestry.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.controller.UserController;
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
	private PageController pageController;
	
	@Inject
	private UserController userController;
	
	private Page page;
	
	private Tag tag;
	
	public Object onActivate(EventContext context) {
		
//		User user = new User();
//		user.setName("Thiago");
//		user.setEmail("thiago@arsmachina.com.br");
//		user.setLogin("thiago");
//		user.setPassword("thiago");
//		user.getRoles().addAll(Arrays.asList(Role.values()));
//		userController.save(user);
		
		page = pageActivationContextService.toPage(context);
		if (page != null) {
			final String firstTagName = page.getFirstTagName();
			if (firstTagName != null) {
				tag = tagController.findByName(firstTagName);
			}
		}
		
		if (tag == null) {
			tag = getMainTag();
		}
		
		// if there's no page directly matching this context and the current tag isn't a blog,
		// we try to find the home page for that tag, which is a page whose URI is the same as the
		// tag name.
		if (page == null && !tag.isBlog()) {
			page = pageController.findByUri(tag.getName());
		}
		

		return page != null || context.getCount() < 1 ? null : Index.class;
		
	}
	
	Object onPassivate() {
		return page != null ? pageActivationContextService.toActivationContext(page.getUri()) : null;
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
	
	public String getTitle() {
		String title;
		if (page != null) {
			title = page.getTitle();
		}
		else {
			title = tag.getTitle();
		}
		return title;
	}

	public String getSubTitle() {
		String subtitle;
		if (page != null) {
			subtitle = page.getSubtitle();
		}
		else {
			subtitle = tag.getSubtitle();
		}
		return subtitle;
	}
	
	/**
	 * Defines the value of the <code>&lt;title&gt;</code> HTML tag.
	 * 
	 * @return a {@link String}.
	 */
	public String getTagTitle() {
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
	
	public String getCssClass() {
		return page != null ? "pageView" : "tagView";
	}
	
	public String getSiteName() {
		Tag tag = getTag();
		final String name;
		if (tag.isSubdomain() || tag.getDomain() != null) {
			name = tag.getTitle();
		}
		else {
			name = getMainTag().getTitle();
		}
		return name;
	}
	
}
