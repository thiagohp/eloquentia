package br.com.arsmachina.eloquentia.tapestry.pages.tag;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.security.SecurityConstants;

/**
 * Page that creates and edit pages.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresRoles(SecurityConstants.AUTHOR)
public class ListTag {
	
	@Inject
	private TagController tagController;
	
	@Inject
	private AlertManager alertManager;
	
	@Property
	private Tag tag;
	
	@Inject 
	private Messages messages;

	// FIXME: implement a GridDataSource
	public List<Tag> getSource() {
		return tagController.findAll();
	}
	
}
