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
package br.com.arsmachina.eloquentia.tapestry.pages;

import java.net.MalformedURLException;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import br.com.arsmachina.eloquentia.controller.UserController;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * Sign up page.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class SignUp {

	@Inject
	private UserController userController;
	
	@Inject
	private SecurityService securityService;
	
	@Environmental
	private ValidationTracker validationTracker;
	
	@Inject
	private Messages messages;
	
	@Property
	private String passwordCheck;
	
	@Property
	private User user;
	
	private String returnUrl;
	
	public void onActivate(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public Object onPassivate() {
		return returnUrl;
	}
	
	void onPrepare() {
		if (user == null) {
			user = new User();
		}
	}
	
	void onValidateFromForm() {
		
		if (passwordCheck != null && passwordCheck.equals(user.getPassword()) == false) {
			validationTracker.recordError(messages.get("eloquentia.passwords-dont-match"));
		}
		
		final String login = user.getLogin();
		if (userController.findByLogin(login) != null) {
			validationTracker.recordError(messages.format("eloquentia.duplicated-username", login));
		}
		
	}
	
	Object onSuccess() throws MalformedURLException {
		
		Subject currentUser = securityService.getSubject();

		if (currentUser == null) {
			throw new IllegalStateException("Subject cannot be null");
		}
		
		final String password = user.getPassword();
		userController.save(user);
		currentUser.login(new UsernamePasswordToken(user.getName(), password));
		
		return returnUrl != null ? new java.net.URL(returnUrl) : null;
		
	}
	
}
