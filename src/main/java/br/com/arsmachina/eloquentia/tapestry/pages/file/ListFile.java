package br.com.arsmachina.eloquentia.tapestry.pages.file;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import br.com.arsmachina.eloquentia.controller.FileController;
import br.com.arsmachina.eloquentia.entity.File;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.security.SecurityConstants;

/**
 * Page that lists the uploaded files.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresRoles(SecurityConstants.AUTHOR)
public class ListFile {
	
	@Inject
	private FileController fileController;
	
	@Inject
	private AlertManager alertManager;
	
	@Property
	private File file;
	
	@Inject 
	private Messages messages;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Inject
	private Locale locale;

	// FIXME: implement a GridDataSource
	public List<File> getSource() {
		return fileController.findAll();
	}

	public String getFileUrl() {
		return pageRenderLinkSource.createPageRenderLinkWithContext(Index.class, file.getName()).toAbsoluteURI();
	}
	
	public String getModifiedDate() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale).format(file.getModifiedDate());
	}
}
