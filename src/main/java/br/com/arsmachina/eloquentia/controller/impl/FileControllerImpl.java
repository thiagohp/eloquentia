package br.com.arsmachina.eloquentia.controller.impl;

import br.com.arsmachina.controller.impl.ControllerImpl;
import br.com.arsmachina.eloquentia.controller.FileController;
import br.com.arsmachina.eloquentia.dao.FileDAO;
import br.com.arsmachina.eloquentia.entity.File;

/**
 * Default {@link FileController} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class FileControllerImpl extends ControllerImpl<File, String> implements FileController {

	final private FileDAO dao;
	
	/**
	 * Single constructor of this class.
	 * 
	 * @param dao a {@link FileDAO}.
	 */
	public FileControllerImpl(FileDAO dao) {
		super(dao);
		this.dao = dao;
	}

	public File findByName(String name) {
		return dao.findByName(name);
	}

}
