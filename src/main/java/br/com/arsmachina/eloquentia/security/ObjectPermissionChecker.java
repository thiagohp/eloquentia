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

import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;

import br.com.arsmachina.eloquentia.entity.User;

/**
 * Service that checks whether the current user
 * has permission to perform a given action, represented by an {@link ObjectAction},
 * in a given object.
 * 
 * @param T the type of the object on which permissions are being checked.
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @tapestrydoc
 */
@UsesOrderedConfiguration(ObjectPermissionChecker.class)
public interface ObjectPermissionChecker<T> {

	/**
	 * Tells whether the current user has permission to perform a given action in the given object.
	 * If this implementation doesn't provide an answer for the received parameters.
	 * it should return <code>null</code>. <code>true</code> and <code>false</code>
	 * return values are considered authoritative answers and no other {@link ObjectPermissionChecker}
	 * will be consulted.
	 * 
	 * @param principalCollection an {@link User}.
	 * @param object a <code>T</code> instance.
	 * @param action an {@link ObjectAction} (except {@link ObjectAction#LIST}).
	 * @return <code>true</code>, <code>false</code> or <code>null</code>.
	 */
	Boolean isPermitted(User user, T object, ObjectAction action);
	
}
