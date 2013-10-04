package br.com.arsmachina.eloquentia.controller.impl;

import br.com.arsmachina.controller.impl.ControllerImpl;
import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.dao.TagDAO;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.services.EloquentiaConstants;

/**
 * Default {@link TagController} implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagControllerImpl extends ControllerImpl<Tag, String> implements TagController {

	final private TagDAO dao;
	
	final private PageController pageController;
	
	/**
	 * Single constructor of this class. Creates the {@link EloquentiaConstants#MAIN_TAG_NAME} tag with default
	 * values if it wasn't created yet.
	 * 
	 * @param dao a {@link TagDAO}.
	 */
	public TagControllerImpl(TagDAO dao, PageController pageController) {
		super(dao);
		this.dao = dao;
		this.pageController = pageController;
		
		Tag mainTag = getMainTag();
		if (mainTag == null) {
			mainTag = new Tag();
			mainTag.setName(EloquentiaConstants.MAIN_TAG_NAME);
			mainTag.setTitle("Please name the " + EloquentiaConstants.MAIN_TAG_NAME + " tag with the name of the blog");
			dao.save(mainTag);
		}
		
	}

	public Tag findByName(String name) {
		Tag tag = dao.findByName(name);
		if (tag == null) {
			if (pageController.isTagReferenced(name)) {
				tag = new Tag();
				tag.setName(name);
				tag.setTitle(name.replaceAll("-", " "));
				save(tag);
			}
		}
		return tag;
	}
	
	public Tag getMainTag() {
		return findByName(EloquentiaConstants.MAIN_TAG_NAME);
	}

	public boolean isSubdomain(String tagName) {
		final Tag tag = findByName(tagName);
		return tag != null && tag.isSubdomain();
	}

}
