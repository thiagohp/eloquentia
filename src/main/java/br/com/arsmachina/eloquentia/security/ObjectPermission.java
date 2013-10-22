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

import org.apache.shiro.authz.Permission;

import br.com.arsmachina.eloquentia.entity.User;

/**
 * {@link Permission} implementation that delegates to {@link ObjectPermissionChecker}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
final public class ObjectPermission implements Permission {
	
	final private User user;
	
	@SuppressWarnings("rawtypes")
	final private ObjectPermissionChecker objectPermissionChecker;

	/**
	 * Single constructor of this class.
	 * @param principalCollection an {@link User}.
	 * @param objectPermissionChecker an {@link ObjectPermissionChecker}.
	 */
	@SuppressWarnings("rawtypes")
	public ObjectPermission(User user, ObjectPermissionChecker objectPermissionChecker) {
		assert user != null;
		assert objectPermissionChecker != null;
		this.user = user;
		this.objectPermissionChecker = objectPermissionChecker;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean implies(Permission p) {
		
		boolean implies = false;
		
		if (p instanceof ObjectActionPermission) {
			final ObjectActionPermission<?> permission = (ObjectActionPermission<?>) p;
			implies = objectPermissionChecker.isPermitted(
					user, permission.getObject(), permission.getAction());
		}
		
		return implies;
		
	}
	
}
