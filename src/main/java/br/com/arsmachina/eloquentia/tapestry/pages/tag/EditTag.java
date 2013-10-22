package br.com.arsmachina.eloquentia.tapestry.pages.tag;

import java.util.Locale;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.entity.Tag.Link;
import br.com.arsmachina.eloquentia.security.SecurityConstants;
import br.com.arsmachina.eloquentia.tapestry.services.UserService;

/**
 * Tag that creates and edit tags.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresRoles(SecurityConstants.AUTHOR)
public class EditTag {
	
	@Inject
	private TagController tagController;
	
	@Inject
	private AlertManager alertManager;

	@Property
	private Tag tag;
	
	@Inject 
	private Messages messages;
	
	@Inject
	private UserService userService;
	
	@Inject
	private Locale locale;
	
	@Environmental
	private ValidationTracker validationTracker;
	
	@InjectComponent("name")
	private Field nameField;
	
	@Property
	private Link link;
	
	Object onActivate(EventContext context) {
		Object returnValue = null;
		if (context.getCount() > 0) {
			tag = tagController.findByName(context.get(String.class, 0));
			returnValue = tag != null ? null : EditTag.class;
		}
		return returnValue;
	}
	
	void onPrepareFromForm() {
		if (tag == null) {
			tag = new Tag();
		}
	}

	void onPrepareFromHeaderLinkForm() {
		if (link == null) {
			link = new Link();
		}
	}

	void onValidateFromName(String name) {
		Tag other = tagController.findByName(name);
		if (other != null && !other.getId().equals(tag.getId())) {
			validationTracker.recordError(nameField, messages.format("eloquentia.duplicated-tag", name));
		}
	}
	
	void onSuccessFromForm() {
		tagController.saveOrUpdate(tag);
		alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, 
				messages.format("eloquentia.tag-saved-successfully", tag.getTitle()));
	}

	void onSuccessFromHeaderLinkForm() {
		tag.getHeaderLinks().add(link);
		onSuccessFromForm();
	}

	public Object onPassivate() {
		return tag != null ? tag.getName() : null;
	}

}
