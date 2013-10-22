package br.com.arsmachina.eloquentia.enums;

/**
 * Enum that represents all the different roles an user can have in Eloquentia.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public enum Role {

	/** Role had by non-logged in users. */
	ANONYMOUS,
	
	/** Role had by all logged-in users. */
	USER,
	
	/** Role allowed to post comments. */
	COMMENTER,
	
	/** Role allowed to posts and edit blog posts and pages. */
	AUTHOR,
	
	/** Role to perform adminstrative actions, such as adding users and assigning permissions. */
	ADMIN
	
}
