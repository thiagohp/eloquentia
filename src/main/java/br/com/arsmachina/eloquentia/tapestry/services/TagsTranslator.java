package br.com.arsmachina.eloquentia.tapestry.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.FormSupport;

import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * {@link Translator} implementation for tag lists.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagsTranslator implements Translator<List<String>> {
	
	final private Validator validator;
	
	final private Messages messages;
	
	/**
	 * Single constructor of this class.
	 * @param validator a {@link Validator}.
	 */
	public TagsTranslator(Validator validator, Messages messages) {
		this.validator = validator;
		this.messages = messages;
	}
	
	/**
	 * Pattern used to separate one tag from the others. 
	 */
	final public static String SEPARATOR = "[\\s]+";
	
	public String getName() {
		return "tags";
	}

	public String toClient(List<String> tags) {
		return Page.toString(tags);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getType() {
		return List.class;
	}

	public String getMessageKey() {
		return "eloquentia.tags-format-error-message";
	}

	public List<String> parseClient(Field field, String clientValue, String message)
			throws ValidationException {
		List<String> list = new ArrayList<String>();
		if (clientValue != null) {
			list.addAll(Arrays.asList(clientValue.split(SEPARATOR)));
		}
		if (!list.isEmpty()) {
			Tag tag = new Tag();
			Set<ConstraintViolation<Tag>> violations;
			for (String tagName : list) {
				tag.setName(tagName);
				violations = validator.validateProperty(tag, "name");
				if (!violations.isEmpty()) {
					throw new ValidationException(messages.format("tags-regexp-message-extended", tagName));
				}
			}
		}
		return list;
	}

	public void render(Field field, String message, MarkupWriter writer,
			FormSupport formSupport) {
	}

}
