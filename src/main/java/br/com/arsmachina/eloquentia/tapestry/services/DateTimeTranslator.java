package br.com.arsmachina.eloquentia.tapestry.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.FormSupport;

import br.com.arsmachina.eloquentia.services.EloquentiaConstants;

/**
 * {@link Translator} implementation for date-time fields.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @see EloquentiaConstants#DATE_TIME_FIELD_FORMAT_SYMBOL
 */
public class DateTimeTranslator implements Translator<Date> {
	
	final private String format;

	/**
	 * Single constructor of this class.
	 * 
	 * @param format a {@link SimpleDateFormat}-valid date-time format definition.
	 */
	public DateTimeTranslator(@Symbol(EloquentiaConstants.DATE_TIME_FIELD_FORMAT_SYMBOL) String format) {
		super();
		this.format = format;
	}

	public String getName() {
		return "datetime";
	}

	public String toClient(Date value) {
		return value != null ? new SimpleDateFormat(format).format(value) : null;
	}

	public Class<Date> getType() {
		return Date.class;
	}

	public String getMessageKey() {
		return "eloquentia.date-time-format-error-message";
	}

	public Date parseClient(Field field, String clientValue, String message)
			throws ValidationException {
		try {
			return clientValue != null ? new SimpleDateFormat(format).parse(clientValue) : null;
		} catch (ParseException e) {
			throw new ValidationException(message);
		}
	}

	public void render(Field field, String message, MarkupWriter writer,
			FormSupport formSupport) {
	}

}
