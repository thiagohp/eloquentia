package br.com.arsmachina.eloquentia.tapestry.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Component that views generates an HTML <code>&lt:time pubdate=""&gt</code> element.
 * The body of the component is ignored. 
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 * @see http://www.w3.org/TR/html5/text-level-semantics.html#the-time-element
 */
public class Time {

	/**
	 * {@link SimpleDateFormat} pattern for the pubdate attribute.
	 */
	final static String PUBDATE_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
	
	@Parameter(allowNull = false, required = true)
	private Date date;
	
	@Inject
	private Locale locale;
	
	boolean beginRender(MarkupWriter writer) {
		writer.element("time", "datetime", formatPubdate()).text(formatDate());
		writer.end();
		return false;
	}

	protected String formatPubdate() {
		return new SimpleDateFormat(PUBDATE_FORMAT).format(date);
	}

	protected String formatDate() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(date);
	}

}
