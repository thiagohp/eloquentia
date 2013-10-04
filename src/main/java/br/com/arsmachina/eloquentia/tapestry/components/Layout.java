package br.com.arsmachina.eloquentia.tapestry.components;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.entity.Tag.Link;
import br.com.arsmachina.eloquentia.services.EloquentiaConstants;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;
import br.com.arsmachina.eloquentia.tapestry.services.UserService;

/**
 * Eloquentia's layout component. FIXME: create a symbol defining the CSS file
 * to be included FIXME: create a service that defines the site name for cases
 * in which different tags are completely different sites/blogs.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@Import(stack = "core", stylesheet = { "classpath:/META-INF/assets/eloquentia/css/eloquentia.css" })
public class Layout {

	/**
	 * Title. Usually, the title of the {@link Page} (post). Not required.
	 */
	@Parameter
	private String title;

	/**
	 * Tag being used to define layout and etc for the website for this request.
	 * Not required.
	 */
	@Parameter(required = true, allowNull = false)
	private Tag tag;

	/**
	 * <code>lang</code> attribute of the <code>&lt;html&gt;</code> tag. Not
	 * required. Default value: <code>en</code>
	 */
	@Parameter(required = false, autoconnect = true, value = "literal:en")
	private String lang;

	/**
	 * Site name. Required. Defaults to the main tag name.
	 */
	@Parameter(required = true, allowNull = false, autoconnect = true)
	private String siteName;

	@Inject
	@Symbol(EloquentiaConstants.GOOGLE_ANALYTICS_KEY_SYMBOL)
	private String googleAnalyticsKey;

	@Inject
	@Symbol(SymbolConstants.HOSTNAME)
	private String hostname;

	@Inject
	private Request request;

	@Inject
	@Property
	private UserService userService;

	@Inject
	private TagController tagController;

	@Inject
	private PageController pageController;

	@Inject
	private PageActivationContextService pageActivationContextService;

	private Link link;

	void onLogout() {
		userService.logout();
		Session session = request.getSession(false);
		if (session != null && !session.isInvalidated()) {
			session.invalidate();
		}
	}

	public String getSubtitle() {
		final String subtitle = tag.getSubtitle();
		return subtitle != null && subtitle.length() > 0 ? subtitle : null;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	Tag defaultTag() {
		return getMainTag();
	}

	@Cached
	public Tag getMainTag() {
		return tagController.getMainTag();
	}

	String defaultSiteName() {
		return getMainTag().getName();
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public boolean isInternalPageLink() {
		String url = extractUri(link.getUrl());
		return pageController.exists(url);
	}

	protected String extractUri(String url) {
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}

	public Object getInternalPageLinkContext() {
		return pageActivationContextService.toActivationContext(extractUri(link
				.getUrl()));
	}

	public String getInternalPageLinkCssClass() {
		return extractUri(request.getPath())
				.endsWith(extractUri(link.getUrl())) ? "active" : "";
	}

	/**
	 * Returns the value of the googleAnalyticsKey field.
	 * 
	 * @return a {@link String}.
	 */
	public String getGoogleAnalyticsKey() {
		return googleAnalyticsKey;
	}

	/**
	 * Returns the value of the hostname field.
	 * @return a {@link String}.
	 */
	public String getHostname() {
		return hostname;
	}
	
	/**
	 * Tells whether Layout should add the Google Analytics API code with subdomain support.
	 * @return a <code>boolean</code>.
	 */
	public boolean isAddGoogleAnalyticsWithSubdomains() {
		return googleAnalyticsKey.trim().length() > 0 && hostname.trim().length() > 0; 
	}

}
