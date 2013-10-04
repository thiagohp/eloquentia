package br.com.arsmachina.eloquentia.dao.mongodb;

import java.io.Serializable;

/**
 * Interface that provides read and write access to the id of a given object.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public interface PrimaryKeyEncoder<T, K extends Serializable> {
	
	/**
	 * Returns the id value of the given object.
	 * @param object the object to read its id from.
	 * @return the id value, which may be null.
	 */
	K get(T object);
	
	/**
	 * Sets the id value of the given object.
	 * @param object the object to have its id set.
	 * @param id the id value.
	 */
	void set(T object, K id);
	
}
