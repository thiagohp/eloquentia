package br.com.arsmachina.eloquentia.tapestry.pages.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import br.com.arsmachina.eloquentia.controller.FileController;
import br.com.arsmachina.eloquentia.entity.File;
import br.com.arsmachina.eloquentia.security.SecurityConstants;
import br.com.arsmachina.eloquentia.tapestry.services.UserService;

/**
 * Tag that creates and edit tags.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@RequiresRoles(SecurityConstants.AUTHOR)
public class EditFile {
	
	@Inject
	private FileController fileController;
	
	@Inject
	private AlertManager alertManager;

	@Property
	private File file;
	
	@Inject 
	private Messages messages;
	
	@Inject
	private UserService userService;
	
	@Inject
	private Locale locale;
	
	@Environmental
	private ValidationTracker validationTracker;
	
	@Property
	private UploadedFile uploadedFile;
	
	@InjectPage
	private ListFile listFile;
	
	void onPrepareFromForm() {
		file = new File();
	}
	
	void onValidateFromForm() throws Exception {
		
		if (uploadedFile != null) {
			final String name = uploadedFile.getFileName();
			file.setName(name);
			final InputStream stream = uploadedFile.getStream();
			if (stream != null) {
				byte[] content = new byte[(int) uploadedFile.getSize()];
				BufferedInputStream bis = null;
				DataInputStream dis = null;
				try {
					bis = new BufferedInputStream(stream);
					dis = new DataInputStream(bis);
					dis.readFully(content);
					file.setContent(content);
				}
				catch (Exception e) {
					validationTracker.recordError(messages.get("eloquentia.error-reading-uploaded-file"));
				}
				finally {
					if (dis != null) {
						dis.close();
					}
					if (bis != null) {
						bis.close();
					}
					if (stream != null) {
						stream.close();
					}
				}
			}
		}
		else {
			validationTracker.recordError(messages.format("eloquentia.no-selected-file", file.getName()));
		}
		
		File other = fileController.findByName(file.getName());
		if (other != null && !other.getId().equals(file.getId())) {
			validationTracker.recordError(messages.format("eloquentia.duplicated-file", file.getName()));
		}
	}
	
	Object onSuccessFromForm() {
		fileController.saveOrUpdate(file);
		alertManager.alert(Duration.SINGLE, Severity.SUCCESS, 
				messages.format("eloquentia.file-saved-successfully", file.getName()));
		return listFile;
	}

}
