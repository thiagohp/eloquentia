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
 * <p>
 * URL rewriter rule that handles domains: 
 * <code>yyy.com</code> -> <code>domain.com/tag/xxx</code>, where xxx is a {@link Tag}
 * with <code>domain</code> property set to <code>yyy</code>. The the base domain is taken
 * from the {@link SymbolConstants#HOSTNAME} symbol. In the example above, the symbol value is
 * <code>domain.com</code>.
 * </p>
 * <p>
 * If the tag isn't a blog ({@link Tag#isBlog()} property false) and there's a page
 * with the same name as the tag, the rewriting is to <code>domain.com/xxx</code>.
 * </p>
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class DomainURLRewriterRule implements URLRewriterRule {
	
	final private TagController tagController;
	
	final private String hostname;
	
	/**
	 * Single constructor of this class.
	 * @param tagController a {@link TagController}.
	 * @param hostname the hostname used by the server running Eloquentia.
	 */
	public DomainURLRewriterRule(
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
		
		if ("/".equals(path)) {
		
			Tag tag = tagController.findByDomain(serverName);
			
			if (tag != null) {
				final String newServerName = hostname.equals(".") ? "localhost" : hostname;
				final String newPath = tag.isBlog() ? "/tag/" + tag.getName() : "/" + tag.getName();
				request = new SimpleRequestWrapper(request, newServerName, newPath);
			}
			
		}
		
		return request;
		
	}

}
