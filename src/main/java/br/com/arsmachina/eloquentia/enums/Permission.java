package br.com.arsmachina.eloquentia.enums;

/**
 * Enum that represents all the different permissions an user can have in Eloquentia.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public enum Permission {

	/** View permission. Only intended for blogs with restricted viewership */
	VIEW,
	/** Permission to post comments. */
	COMMENT,
	/** Permission to post new blog posts and pages. */
	POST,
	/** Permission to perform adminstrative actions, such as adding users */
	ADMIN,
	
}
