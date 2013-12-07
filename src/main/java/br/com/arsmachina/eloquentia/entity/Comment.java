package br.com.arsmachina.eloquentia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.mongojack.DBRef;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that represents comments to pages (posts).
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class Comment implements Article, Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String content;

	private Date posted;

	private DBRef<User, String> postedByRef;
	
	private DBRef<Page, String> pageRef;

	private DBRef<Comment, String> parentRef;

	@JsonIgnore
	transient private User postedBy; 

	@JsonIgnore
	transient private Page page; 

	@JsonIgnore
	transient private Comment parent; 

	private List<String> tags = new ArrayList<String>();
	
	/**
	 * Returns the id of this comment.
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
	@Size(min = 1, max = 10000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@NotNull
	@Past
	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	@NotNull
	@ObjectId
	public DBRef<User, String> getPostedByRef() {
		return postedByRef;
	}

	public void setPostedByRef(DBRef<User, String> postedByRef) {
		this.postedByRef = postedByRef;
	}
	
	@ObjectId
	public DBRef<Comment, String> getParentRef() {
		return parentRef;
	}

	public void setParentRef(DBRef<Comment, String> parentRef) {
		this.parentRef = parentRef;
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
	
	@ObjectId
	@NotNull
	public DBRef<Page, String> getPageRef() {
		return pageRef;
	}

	public void setPageRef(DBRef<Page, String> pageRef) {
		this.pageRef = pageRef;
	}

	@JsonIgnore
	public Page getPage() {
		if ((page == null && pageRef != null) || 
				(page != null && pageRef != null && page.getId() != null && pageRef.getId() != null && !pageRef.getId().equals(page.getId()))) {
			page = pageRef.fetch();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
		if (page != null) {
			pageRef = new DBRef<Page, String>(page.getId(), "page");
		}
	}

	@JsonIgnore
	public Comment getParent() {
		if ((parent == null && parentRef != null) || 
				(parent != null && parentRef != null && parent.getId() != null && parent.getId() != null && !parentRef.getId().equals(parent.getId()))) {
			parent = parentRef.fetch();
		}
		return parent;
	}

	public void setComment(Comment parent) {
		this.parent = parent;
		if (parent != null) {
			parentRef = new DBRef<Comment, String>(parent.getId(), "comment");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Comment)) {
			return false;
		}
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment [content=" + content + ", postedByRef=" + postedByRef
				+ ", pageRef=" + pageRef + "]";
	}

}
