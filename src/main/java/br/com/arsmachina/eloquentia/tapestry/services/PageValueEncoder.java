package br.com.arsmachina.eloquentia.tapestry.services;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;

public class PageValueEncoder implements ValueEncoder<Page>, ValueEncoderFactory<Page> {
	
	final private PageController pageController;

	/**
	 * Single constructor of this class.
	 * 
	 * @param pageController a {@link PageController}.
	 */
	public PageValueEncoder(PageController pageController) {
		this.pageController = pageController;
	}

	public ValueEncoder<Page> create(Class<Page> type) {
		return this;
	}

	public String toClient(Page page) {
		return page != null ? page.getUri() : null;
	}

	public Page toValue(String clientValue) {
		Page page = null;
		if (clientValue != null) {
			page = pageController.findByUri(clientValue);
		}
		return page;
	}

}
