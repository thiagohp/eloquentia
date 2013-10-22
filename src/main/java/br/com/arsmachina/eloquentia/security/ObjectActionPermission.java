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

import java.io.Serializable;

import org.apache.shiro.authz.Permission;

/**
 * {@link Permission} implementation related to actions to a given object. This
 * is basically an permission that carries information for other
 * {@link Permission} implementations to use.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @param T
 *            the type of the object
 * @see ObjectAction
 */
final public class ObjectActionPermission<T extends Serializable> implements Permission, Serializable {

	private static final long serialVersionUID = 1L;

	final private T object;

	final private ObjectAction action;

	/**
	 * Single constructor of this class.
	 * 
	 * @param object a <code>T</code> instance.
	 * @param action an {@link ObjectAction}.
	 */
	public ObjectActionPermission(T object, ObjectAction action) {
		assert object != null;
		assert action != null;
		this.object = object;
		this.action = action;
	}

	/**
	 * Returns the value of the object field.
	 * 
	 * @return a {@link T}.
	 */
	public final T getObject() {
		return object;
	}

	/**
	 * Returns the value of the action field.
	 * 
	 * @return a {@link ObjectAction}.
	 */
	public final ObjectAction getAction() {
		return action;
	}

	@Override
	public boolean implies(Permission p) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ObjectActionPermission)) {
			return false;
		}
		ObjectActionPermission other = (ObjectActionPermission) obj;
		if (action != other.action) {
			return false;
		}
		if (object == null) {
			if (other.object != null) {
				return false;
			}
		} else if (!object.equals(other.object)) {
			return false;
		}
		return true;
	}

}
