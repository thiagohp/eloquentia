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
 * Class that defines constants used in security (authorization and authentication) in Eloquentia.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class SecurityConstants {

	private SecurityConstants() {}
	
	/**
	 * Name of the anonymous role.
	 * @see Role#ANONYMOUS.
	 */
	final public static String ANONYMOUS = "ANONYMOUS";

	/**
	 * Name of the user role.
	 * @see Role#USER.
	 */
	final public static String USER = "USER";

	/**
	 * Name of the commenter role.
	 * @see Role#COMMENTER.
	 */
	final public static String COMMENTER = "COMMENTER";
	
	/**
	 * Name of the author role.
	 * @see Role#AUTHOR.
	 */
	final public static String AUTHOR = "AUTHOR";

	/**
	 * Name of the administrator role.
	 * @see Role#ADMIN.
	 */
	final public static String ADMIN = "ADMIN";
	
	/**
	 * Contant used in the second level of Eloquentia wildcard permissions to denote edition of an
	 * existing object.
	 * 
	 * @see WildcardPermission
	 * @see ObjectAction#EDIT
	 */
	final public static String EDIT_PERMISSION = ObjectAction.EDIT.getPermissionName();
	
	/**
	 * Contant used in the second level of Eloquentia wildcard permissions to denote edition of viewing
	 * an object.
	 * 
	 * @see WildcardPermission
	 * @see ObjectAction#VIEW
	 */
	final public static String VIEW_PERMISSION = ObjectAction.VIEW.getPermissionName();
	
	/**
	 * Contant used in the second level of Eloquentia wildcard permissions to denote edition of deleting
	 * an object.
	 * 
	 * @see WildcardPermission
	 * @see ObjectAction#DELETE
	 */
	final public static String DELETE_PERMISSION = ObjectAction.DELETE.getPermissionName();

}
