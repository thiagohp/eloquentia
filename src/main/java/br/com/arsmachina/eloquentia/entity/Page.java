package br.com.arsmachina.eloquentia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mongojack.DBRef;
import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class that represents pages (posts) in the blog. It's called "page" and not "post" because,
 * in Eloquentia, all posts are pages, but not all pages are posts, so it can be used as a simple
 * CMS too.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@JsonIgnoreProperties("views")
@MongoCollection(name = "page")
public class Page implements Article, Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String uri;

	private String title;

	private String teaser;
	
	private String content;
	
	private String css;

	private Date posted;

	private Date edited;

	private DBRef<User, String> postedByRef;
	
	@JsonIgnore
	transient private User postedBy; 

	private List<String> tags = new ArrayList<String>();
	
	/**
	 * Returns the id of this page. It's also used to create the page URL. It must be unique
	 * among all pages in the same Eloquentia instance.
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

	@NotNull
	@Size(min = 1, max = 140)
	@Pattern(regexp = "[a-z0-9\\-]+([\\/]?[a-z0-9\\-])+")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@NotNull
	@Size(max = 140)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Size(min = 1, max = 100000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Size(min = 1, max = 100000)
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	/**
	 * Quick description of the page meant to be used in page listings and RSS feeds.  
	 * 
	 * @return a {@link String}.
	 */
	@Size(min = 1, max = 1000)
	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	@NotNull
	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	@Past
	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
	}

	@NotNull
	@ObjectId
	public DBRef<User, String> getPostedByRef() {
		return postedByRef;
	}

	public void setPostedByRef(DBRef<User, String> postedByRef) {
		this.postedByRef = postedByRef;
	}

	@NotNull
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@JsonIgnore
	public User getPostedBy() {
		if ((postedBy == null && postedByRef != null) || 
				(postedBy != null && postedByRef != null && postedBy.getId() != null && postedByRef.getId() != null && !postedByRef.getId().equals(postedBy.getId()))) {
			postedBy = postedByRef.fetch();
		}
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
		if (postedBy != null) {
			postedByRef = new DBRef<User, String>(postedBy.getId(), "user");
		}
	}

	@Override
	public String toString() {
		return "Page [uri=" + uri + ", title=" + title + ", posted=" + posted
				+ ", postedBy=" + getPostedBy() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Page other = (Page) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	/**
	 * Returns the name of the first tag of this page
	 * 
	 * @return a {@link String} or <code>null</code>
	 */
	@JsonIgnore
	public String getFirstTagName() {
		return !tags.isEmpty() ? getTags().get(0) : null;
	}
	
	/**
	 * Converts a list of tags into a single String.
	 * 
	 * @param tags a {@link List} of {@link String}s.
	 * @return a {@link String}.
	 */
	public static final String toString(List<String> tags) {
		String clientValue = "";
		if (tags.size() >= 1) {
			clientValue = tags.get(0);
		}
		if (tags.size() > 1) {
			StringBuilder builder = new StringBuilder(clientValue);
			for (int i = 1; i < tags.size(); i++) {
				builder.append(" ");
				builder.append(tags.get(i));
			}
			clientValue = builder.toString();
		}
		return clientValue;
	}

}
