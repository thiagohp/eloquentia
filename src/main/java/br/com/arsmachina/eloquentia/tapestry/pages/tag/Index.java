package br.com.arsmachina.eloquentia.tapestry.pages.tag;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.HttpError;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * Page that shows the pages associated with a given tab. 
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 *
 */
public class Index {

	@Inject
	private TagController tagController;
	
	@Inject
	private Messages messages;
	
	private Tag tag;
	
	Object onActivate(EventContext context) {
		Object returnValue = null;
		final String tagName = context.get(String.class, 0);
		if (context.getCount() == 1) {
			setTag(tagController.findByName(tagName));
		}
		if (getTag() == null) {
			 returnValue = new HttpError(404, messages.format("eloquentia.error-tag-not-found", tagName));
		}
		return returnValue;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public Tag getMainTag() {
		return tagController.getMainTag();
	}
	
}
