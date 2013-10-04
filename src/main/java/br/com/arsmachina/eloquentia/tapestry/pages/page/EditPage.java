package br.com.arsmachina.eloquentia.tapestry.pages.page;

import java.util.Date;
import java.util.Locale;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;
import br.com.arsmachina.eloquentia.tapestry.services.UserService;

/**
 * Page that creates and edit pages.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresAuthentication // FIXME: change check to role or permission
public class EditPage {
	
	@Inject
	private PageController pageController;
	
	@Inject
	private AlertManager alertManager;

	@Property
	private Page page;
	
	@Inject 
	private Messages messages;
	
	@Inject
	private UserService userService;
	
	@Inject
	private Locale locale;
	
	@Environmental
	private ValidationTracker validationTracker;
	
	@InjectComponent("uri")
	private Field uriField;
	
	@Inject
	private PageActivationContextService pageActivationContextService;
	
	Object onActivate(EventContext context) {
		Object returnValue = null;
		if (context.getCount() > 0) {
			page = pageActivationContextService.toPage(context, false);
			returnValue = page != null ? null : EditPage.class;
		}
		return returnValue;
	}
	
	void onPrepare() {
		if (page == null) {
			page = new Page();
			page.setPosted(new Date());
			page.setPostedBy(userService.getUser());
		}
	}

	void onValidateFromUri(String uri) {
		Page other = pageController.findByUri(uri);
		if (other != null && !other.getId().equals(page.getId())) {
			validationTracker.recordError(uriField, messages.format("eloquentia.duplicated-uri", uri));
		}
	}
	
	void onSuccess() {
		pageController.saveOrUpdate(page);
		alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, 
				messages.format("eloquentia.page-saved-successfully", page.getTitle()));
	}
	
	public Object getContext() {
		return pageActivationContextService.toActivationContext(page);
	}

	public Object onPassivate() {
		return pageActivationContextService.toActivationContext(page);
	}

	public JSONObject getOptions() {
		return new JSONObject("lang", locale.getLanguage()).put("containersItems", 
				new JSONArray().put(new JSONObject("name", "article", "title", "Article", "css", "wym_containers_article")));
	}
	
//	public FieldTranslator<Date> getDateTimeTranslator() {
//		return fieldTranslatorSource.
//	}

}
