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

/**
 * Enum that represents the possible actions for objects of a given type.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public enum ObjectAction {

	/**
	 * Edit an existing one.
	 */
	EDIT,
	
	/**
	 * View an object.
	 */
	VIEW,
	
	/**
	 * Delete (remove) an object.
	 */
	DELETE;
	
	final private String permissionName = this.name().toLowerCase();
	
	/**
	 * Returns the permission name of this action. This isn't the user-readable name.
	 * @return a {@link String}.
	 */
	public final String getPermissionName() {
		return permissionName;
	}
	
}
