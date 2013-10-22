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
package br.com.arsmachina.eloquentia.tapestry.urlrewriting;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.LinkTransformer;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;

import br.com.arsmachina.eloquentia.EloquentiaConstants;
import br.com.arsmachina.eloquentia.controller.TagController;

/**
 * {@link LinkTransformer} that does <code>domain.com/tag/xxx</code> ->  
 * {@link Link} rewritings. <code>xxx.domain.com</code>.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @see SubdomainURLRewriterRule
 */
public class SubdomainTagLinkTransformer implements PageRenderLinkTransformer {
	
	final private Request request;
	
	final private TagController tagController;
	
	final private String hostname;
	
	final private boolean enabled;
	
	private String port;
	
	/**
	 * Single constructor of this class.
	 * 
	 * @param tagController a {@link TagController}.
	 * @param hostname the hostname used by the server running Eloquentia.
	 */
	public SubdomainTagLinkTransformer(
			final Request request,
			final TagController tagController, 
			@Inject @Symbol(SymbolConstants.HOSTNAME) final String hostname) {
		
		assert request != null;
		assert tagController != null;
		assert hostname != null;
		
		this.request = request;
		this.tagController = tagController;
		this.hostname = hostname.trim();
		this.enabled = this.hostname.length() > 0;
		
	}

	public Link transformPageRenderLink(Link defaultLink, PageRenderRequestParameters parameters) {

		Link link = null;
		
		if (port == null) {
			final int portNumber = request.getServerPort();
			port = portNumber == 80 ? "" : ":" + portNumber;
		}
		
		final EventContext activationContext = parameters.getActivationContext();
		if (enabled && parameters.getLogicalPageName().equals("tag/Index") && activationContext.getCount() > 0) {
			
			final String tagName = activationContext.get(String.class, 0);
			
			// avoid /tag/www and use just 
			if (tagName.equals(EloquentiaConstants.MAIN_TAG_NAME)) {
				link = new SimpleLink(String.format("http://%s%s", hostname, port));
			}
			
			// the tag must exist and have it marked as subdomain for the rewriting to happen.
			else if (tagController.isSubdomain(tagName)) {
				link = new SimpleLink(String.format("http://%s.%s%s", tagName, hostname, port));
			}
			
			// change of subdomain
			else {
				final String url = defaultLink.getBasePath().replace("http://", "");
				if (!url.startsWith(hostname)) {
					link = new SimpleLink(String.format("http://%s%s/tag/%s", hostname, port, tagName));
				}
			}

		}
		
		return link;
		
	}

	/**
	 * Returns null, as the incoming URL rewriting is done in {@link SubdomainURLRewriterRule}.
	 */
	public PageRenderRequestParameters decodePageRenderRequest(Request request) {
		return null;
	}

}
