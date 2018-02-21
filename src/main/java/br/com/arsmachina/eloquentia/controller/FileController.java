package br.com.arsmachina.eloquentia.controller;

import br.com.arsmachina.controller.Controller;
import br.com.arsmachina.eloquentia.entity.File;

/**
 * Controller interface for {@link File}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface FileController extends Controller<File, String> {
	
	/**
	 * Finds a {@link File} by its name.
	 * 
	 * @param uri a {@link String}.
	 * @return a {@link File} or <code>null</code>.
	 */
	File findByName(String name);
	
}
