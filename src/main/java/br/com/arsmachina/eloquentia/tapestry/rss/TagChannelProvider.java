// Copyright 2013 Thiago H. de Paula Figueiredo
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// Copyright 2013 Thiago H. de Paula Figueiredo
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package br.com.arsmachina.eloquentia.tapestry.rss;

import java.util.List;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.eloquentia.tapestry.pages.Index;
import br.com.arsmachina.eloquentia.tapestry.services.PageActivationContextService;
import br.com.arsmachina.tapestry_rss.Channel;
import br.com.arsmachina.tapestry_rss.Item;
import br.com.arsmachina.tapestry_rss.pages.Rss;
import br.com.arsmachina.tapestry_rss.services.ChannelProvider;

/**
 * {@link ChannelProvider} implementation: tag as channel, pages as items.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagChannelProvider implements ChannelProvider {
	
	final private TagController tagController;
	
	final private PageController pageController;
	
	final private PageRenderLinkSource pageRenderLinkSource;
	
	final private PageActivationContextService pageActivationContextService;
	
	/**
	 * Single constructor of this class.
	 * @param tagController a {@link TagController}.
	 * @param pageController a {@link PageController}.
	 * @param pageActivationContextService a {@link PageActivationContextService}.
	 * @param pageRenderLinkSource a {@link PageRenderLinkSource}.
	 */
	public TagChannelProvider(final TagController tagController, final PageController pageController,
			final PageRenderLinkSource pageRenderLinkSource,
			final PageActivationContextService pageActivationContextService) {
		assert pageController != null;
		assert tagController != null;
		assert pageRenderLinkSource != null;
		assert pageActivationContextService != null;
		this.tagController = tagController;
		this.pageController = pageController;
		this.pageRenderLinkSource = pageRenderLinkSource;
		this.pageActivationContextService = pageActivationContextService;
	}

	public Channel getChannel(EventContext context) {
		
		Channel channel = null;
		
		if (context.getCount() == 2 && "tag".equals(context.get(String.class, 0))) {
			final String tagName = context.get(String.class, 1);
			final Tag tag = tagController.findByName(tagName);
			if (tag != null) {
				final Link link = pageRenderLinkSource.createPageRenderLinkWithContext(Rss.class, "tag", tag.getName());
				channel = new Channel(tag.getTitle(), link.toAbsoluteURI(), tag.getSubtitle());
				final List<Page> pages = pageController.findByTag(tagName, 0, 10); // FIXME: let the maximum number of pages to be configurable
				Item item;
				for (Page page : pages) {
					item = new Item();
					item.setDescription(page.getTeaser());
					item.setLink(getLink(page));
					item.setPublicationDate(page.getPosted());
					item.setTitle(page.getTitle());
					channel.getItems().add(item);
				}
			}
		}
		
		return channel;
		
	}

	private String getLink(Page page) {
		final Object activationContext = pageActivationContextService.toActivationContext(page);
		Link link;
		if (activationContext instanceof List) {
			List<?> list = (List<?>) activationContext;
			link = pageRenderLinkSource.createPageRenderLinkWithContext(Index.class, list.toArray());
		}
		else {
			link = pageRenderLinkSource.createPageRenderLinkWithContext(Index.class, activationContext);
		}
		return link.toAbsoluteURI();
	}

}
