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
package br.com.arsmachina.eloquentia.tapestry.services;

import org.apache.tapestry5.ioc.services.Coercion;

import br.com.arsmachina.eloquentia.entity.Tag;
import br.com.arsmachina.tapestry_rss.Channel;

/**
 * Coercion from {@link Tag} to {@link Channel}.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagChannelCoercion implements Coercion<Tag, Channel> {

	/* (non-Javadoc)
	 * @see org.apache.tapestry5.ioc.services.Coercion#coerce(java.lang.Object)
	 */
	public Channel coerce(Tag input) {
		// TODO Auto-generated method stub
		return null;
	}

}
