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
package br.com.arsmachina.eloquentia.tapestry.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

import br.com.arsmachina.eloquentia.entity.Article;

/**
 * Component that renders a header for an article, which can be a page or a comment.
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @tapestrydoc
 */
@SupportsInformalParameters
public class ArticleHeader {

	/**
	 * Article to render a header for.
	 */
	@Parameter(required = true, allowNull = false)
	private Article article;
	
	@Inject
	private ComponentResources resources;
	
	private String elementName;

	/**
	 * Renders the wrapping element if such.
	 * @param writer a {@link MarkupWriter}.
	 * @return <code>false</code>.
	 */
	boolean beforeRender(MarkupWriter writer) {
		elementName = resources.getElementName();
		if (elementName != null) {
			writer.element(elementName);
			resources.renderInformalParameters(writer);
		}
		return false;
	}
	
	/**
	 * Closes the wrapping element if such.
	 * @param writer a {@link MarkupWriter}.
	 */
	void afterRender(MarkupWriter writer) {
		if (elementName != null) {
			writer.end();
		}
	}

	/**
	 * Returns the value of the article field.
	 * @return a {@link Article}.
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * Sets the value of the article field.
	 * @param article a {@link Article}.
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

}
