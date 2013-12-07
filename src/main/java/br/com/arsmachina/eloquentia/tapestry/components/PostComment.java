package br.com.arsmachina.eloquentia.tapestry.components;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import br.com.arsmachina.eloquentia.controller.CommentController;
import br.com.arsmachina.eloquentia.entity.Comment;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.tapestry.services.UserService;

/**
 * Component that allows the posting of a comment to a {@link Page}, optionally answering another 
 * comment. 
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@SupportsInformalParameters
public class PostComment {
	
	@Mixin
	private RenderInformals renderInformals;

	/**
	 * The page to be viewed. If not provided, the component will search for a page using the
	 * <code>uri</code> parameter.
	 */
	@Parameter(allowNull = false, required = true)
	private Page page;
	
	/**
	 * Comment to which this comment is replying.
	 */
	@Parameter
	private Comment parent;
	
	@Inject
	private CommentController commentController;
	
	@Inject
	private UserService userService;
	
	@Inject
	private Messages messages;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private AlertManager alertManager;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	private Comment comment;
	
	private String newCommentText;
	
	@Cached
	public List<Comment> getComments() {
		return commentController.findByPage(page);
	}
	
	public String getCommentCountParagraph() {
		return messages.format("eloquentia.comment-count", getComments().size());
	}
	
	public String getReturnUrl() {
		return pageRenderLinkSource.createPageRenderLink(resources.getPageName()).toAbsoluteURI();
	}

	/**
	 * Returns the value of the comment field.
	 * @return a {@link Comment}.
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * Sets the value of the comment field.
	 * @param comment a {@link Comment}.
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * Returns the value of the newCommentText field.
	 * @return a {@link String}.
	 */
	public String getNewCommentText() {
		return newCommentText;
	}

	/**
	 * Sets the value of the newCommentText field.
	 * @param newCommentText a {@link String}.
	 */
	public void setNewCommentText(String newCommentText) {
		this.newCommentText = newCommentText;
	}
	
	/**
	 * Returns the CSS "style" attribute value for the post comment div.
	 * @return a {@link String}.
	 */
	public String getCssStyle() {
		return comment == null ? "display: none" : "";
	}

	/**
	 * Saves the comment.
	 */
	void onSuccessFromPostComment() {
		
		Comment comment = new Comment();
		comment.setPostedBy(userService.getUser());
		comment.setContent(newCommentText);
		comment.setPage(page);
		commentController.save(comment);
		
		alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, messages.get("eloquentia.comment-posted-successfully"));
		
	}

	/**
	 * Returns the value of the parent field.
	 * @return a {@link Comment}.
	 */
	public Comment getParent() {
		return parent;
	}

	/**
	 * Sets the value of the parent field.
	 * @param parent a {@link Comment}.
	 */
	public void setParent(Comment parent) {
		this.parent = parent;
	}

}
