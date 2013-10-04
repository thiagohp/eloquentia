package br.com.arsmachina.eloquentia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mongojack.ObjectId;

/**
 * Class that represents a tag. Notice that there may be tags associated with
 * pages which don't have an associated {@link Tag} instance. The
 * <code>www</code> tag has a special meaning: it provides the information about
 * the blog as a whole, not just for the tag itself.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
// @MongoCollection(name = "tag")
public class Tag {

	private String id;

	private String name;

	private String title;

	private String subtitle;
	
	private boolean subdomain;
	
	private List<Link> headerLinks = new ArrayList<Link>(); 

	/**
	 * Returns the internal database id.
	 * 
	 * @return a {@link String}.
	 */
	@Id
	@ObjectId
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Name of the tag. Required. Intended for use in URLs. It should be
	 * comprised of only lowercase letters, numbers or dashes ("-") and have a
	 * maximum length of 50.
	 * 
	 * @return a {@link String}.
	 */
	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "[a-z0-9\\-]+")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Title of the tag. Required. Used as the user-presentable label, not in
	 * URLs.
	 * 
	 * @return a {@link String}.
	 */
	@NotNull
	@Size(min = 1, max = 140)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Subtitle of the tag. Not required. Used when the tag is being shown.
	 * 
	 * @return a {@link String}.
	 */
	@Size(min = 1, max = 350)
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	/**
	 * Links to be generated in the header. 
	 * @return a {@link List} of {@link Link} instances.
	 */
	@NotNull
	@Valid
	public List<Link> getHeaderLinks() {
		return headerLinks;
	}

	public void setHeaderLinks(List<Link> headerLinks) {
		this.headerLinks = headerLinks;
	}

	/**
	 * Defines whether a tag should be used as a subdomain.
	 * @return a <code>boolean</code>.
	 */
	public boolean isSubdomain() {
		return subdomain;
	}

	public void setSubdomain(boolean subdomain) {
		this.subdomain = subdomain;
	}



	/**
	 * Class that represents a link.
	 * 
	 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
	 */
	public static final class Link implements Serializable {

		private static final long serialVersionUID = 1L;

		private String url;

		private String label;

		/**
		 * Returns the URL of this link.
		 * @return a {@link String}.
		 */
		@NotNull
		@Size(min = 1, max = 200)
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * Returns the label of this link.
		 * @return a {@link String}.
		 */
		@NotNull
		@Size(min = 1, max = 50)
		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Link other = (Link) obj;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Link [url=" + url + ", label=" + label + "]";
		}

	}

}
