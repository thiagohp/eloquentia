package br.com.arsmachina.eloquentia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mongojack.ObjectId;

import br.com.arsmachina.eloquentia.enums.Permission;

/**
 * Class that represents users of the blog.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String login;

	private String email;

	private String name;

	private String password;

	private List<Permission> permissions = new ArrayList<Permission>();

	@Id
	@ObjectId
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 3)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Size(min = 3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Size(min = 3)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotNull
	@Size(min = 10)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	@Size(min = 1)
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name
				+ ", password=" + password + ", permissions=" + permissions + "]";
	}

}
