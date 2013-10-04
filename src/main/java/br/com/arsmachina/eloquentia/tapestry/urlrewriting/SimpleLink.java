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

import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.LinkSecurity;

/**
 * Simple {@link Link} implementation that receives the full URL in its constructor and returns it
 * unchanged.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class SimpleLink implements Link {
	
	final private String url;
	
	/**
	 * Single constructor of this class.
	 * @param url a {@link String} containing the URL.
	 */
	public SimpleLink(String url) {
		assert url != null && url.trim().length() > 0;
		this.url = url;
	}

	/**
	 * Returns an empty list.
	 */
	public List<String> getParameterNames() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public String getParameterValue(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public Link addParameter(String parameterName, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public Link addParameterValue(String parameterName, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public Link removeParameter(String parameterName) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns "/".
	 */
	public String getBasePath() {
		return "/";
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public Link copyWithBasePath(String basePath) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the URL passed in the constructor.
	 */
	public String toURI() {
		return url;
	}

	/**
	 * Returns the URL passed in the constructor.
	 */
	public String toRedirectURI() {
		return url;
	}

	/**
	 * Returns <code>null</code>
	 */
	public String getAnchor() {
		return null;
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public Link setAnchor(String anchor) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the URL passed in the constructor.
	 */
	public String toAbsoluteURI() {
		return url;
	}

	/**
	 * Returns the URL passed in the constructor.
	 */
	public String toAbsoluteURI(boolean secure) {
		return url;
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public void setSecurity(LinkSecurity newSecurity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public LinkSecurity getSecurity() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 */
	public String[] getParameterValues(String parameterName) {
		throw new UnsupportedOperationException();
	}

}
