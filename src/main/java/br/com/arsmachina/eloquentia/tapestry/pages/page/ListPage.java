package br.com.arsmachina.eloquentia.tapestry.pages.page;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;

/**
 * Page that creates and edit pages.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresAuthentication
public class ListPage {
	
	@Inject
	private PageController pageController;
	
	@Inject
	private AlertManager alertManager;
	
	@Inject
	private PageActivationContextService pageActivationContextService;
	
	@Property
	private Page page;
	
	@Inject 
	private Messages messages;

	// FIXME: implement a GridDataSource
	public List<Page> getSource() {
		return pageController.findAll();
	}
	
	public Object getContext() {
		return pageActivationContextService.toActivationContext(page);
	}
	
}
