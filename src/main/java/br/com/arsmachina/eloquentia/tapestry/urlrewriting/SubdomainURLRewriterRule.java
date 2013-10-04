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

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.urlrewriter.SimpleRequestWrapper;
import org.apache.tapestry5.urlrewriter.URLRewriterRule;

import br.com.arsmachina.eloquentia.controller.TagController;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * URL rewriter rule that handles subdomains: 
 * <code>xxx.domain.com</code> -> <code>domain.com/tag/xxx</code>, where xxx is a {@link Tag}
 * with the <code>subdomain</code> boolean property set to true. The the base domain is taken
 * from the {@link SymbolConstants#HOSTNAME} symbol. In the example above, the symbol value is
 * <code>domain.com</code>. 
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class SubdomainURLRewriterRule implements URLRewriterRule {
	
	final private TagController tagController;
	
	final private String hostname;
	
	/**
	 * Single constructor of this class.
	 * @param tagController a {@link TagController}.
	 * @param hostname the hostname used by the server running Eloquentia.
	 */
	public SubdomainURLRewriterRule(
			final TagController tagController, 
			@Inject @Symbol(SymbolConstants.HOSTNAME) final String hostname) {
		assert tagController != null;
		assert hostname != null;
		this.tagController = tagController;
		this.hostname = "." + hostname;
	}

	public Request process(Request request) {
		
		final String serverName = request.getServerName();
		final String path = request.getPath();
		
		// this rewriting should only happen when a request to
		// tagName + '.' + hostname is done.
		if (path.equals("/") && serverName.endsWith(hostname)) {
			
			final String tagName = serverName.substring(0, serverName.indexOf('.'));
			final Tag tag = tagController.findByName(tagName);
			
			// the tag must exist and have it marked as subdomain for the rewriting to happen.
			if (tag != null && tag.isSubdomain()) {
				request = new SimpleRequestWrapper(request, hostname, "/tag/" + tagName);
			}
			
		}
		
		return request;
		
	}

}
