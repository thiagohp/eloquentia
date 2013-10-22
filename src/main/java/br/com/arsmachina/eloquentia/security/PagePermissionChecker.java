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
package br.com.arsmachina.eloquentia.security;

import br.com.arsmachina.eloquentia.controller.PageController;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * {@link ObjectPermissionChecker} implementation for {@link Page} objects.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class PagePermissionChecker implements ObjectPermissionChecker<Page> {
	
	final private PageController pageController;
	
	/**
	 * Single constructor of this class.
	 * @param pageController a {@link PageController}.
	 */
	public PagePermissionChecker(PageController pageController) {
		assert pageController != null;
		this.pageController = pageController;
	}

	@Override
	public Boolean isPermitted(User user, Page object, ObjectAction action) {
		
		boolean permitted = false;
		if (object instanceof Page) {
			permitted = pageController.isPermitted(user, object, action);
		}
		
		return permitted;
		
	}

}
