package br.com.arsmachina.eloquentia.tapestry.components;

import java.util.Date;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractTextField;

/**
 * Component used to edit date and time in a single field. For now, it only uses the
 * Brazilian format for date and time.
 * Based on <a href="http://tarruda.github.io/bootstrap-datetimepicker/">bootstrap-datetimepicker</a>.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class DateTimePicker extends AbstractTextField {
	
	@Parameter
	private Date date;

	@Override
	protected void writeFieldTag(MarkupWriter writer, String value) {
		
	}

}
