package br.com.arsmachina.eloquentia.tapestry.components;

import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.EloquentiaConstants;
import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * View pages by tags.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class ViewPagesByTag {

	/**
	 * Tag used to filter the pages.
	 */
	@Parameter(required = true, allowNull = false)
	private Tag tag;

	/**
	 * How many pages should be shown at a time.
	 */
	@Parameter(value = "symbol:" + EloquentiaConstants.PAGES_PER_PAGINATION_SYMBOL)
	private int pagesPerPagination;

	/**
	 * Number of the page to be shown. First page is 1.
	 */
	@Parameter("1")
	private int pageNumber;
	
	private Page page;

	@Inject
	private PageController pageController;

	public List<Page> getPages() {
		return pageController.findByTag(tag, (pageNumber - 1) * pagesPerPagination, pagesPerPagination);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
