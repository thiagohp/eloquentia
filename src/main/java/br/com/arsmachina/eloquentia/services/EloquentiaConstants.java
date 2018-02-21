package br.com.arsmachina.eloquentia.services;

/**
 * Class that defines the names of symbols used to configure Eloquentia.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class EloquentiaConstants {

	/**
	 * Name of the Tapestry-IoC symbol which defines the format for date-time fields.
	 */
	public static final String DATE_TIME_FIELD_FORMAT_SYMBOL = "eloquentia.date-time-field-format";
	
	/**
	 * Default {@link #DATE_TIME_FIELD_FORMAT_SYMBOL} value.
	 */
	public static final String DEFAULT_DATE_TIME_FIELD_FORMAT_SYMBOL = "dd/MM/yyyy HH:mm";
	
	/**
	 * Name of the Tapestry-IoC symbol which defines how many pages should be shown at a time in pagination.
	 */
	public static final String PAGES_PER_PAGINATION_SYMBOL = "eloquentia.pages-per-pagination";

	/**
	 * Default {@link #PAGES_PER_PAGINATION_SYMBOL} value.
	 */
	public static final String DEFAULT_PAGES_PER_PAGINATION = "10";

	/**
	 * Name of the Tapestry-IoC symbol which defines the prefix added to a tag to form its CSS class.
	 */
	public static final String TAG_CSS_CLASS_PREFIX_SYMBOL = "eloquentia.tag-css-class-prefix";

	/**
	 * Default {@link #TAG_CSS_CLASS_PREFIX_SYMBOL} value.
	 */
	public static final String DEFAULT_TAG_CSS_CLASS_PREFIX = "tag-";

	/**
	 * Tag used to define the title and subtitle of the whole blog/site.
	 */
	final public static String MAIN_TAG_NAME = "www";

	/**
	 * Name of the tag which enables the Tapestry Syntax Highlight integration when
	 * found in the tag list of a page.
	 */
	public static final String SYNTAX_HIGHLIGHT_TAG = "syntax-highlight";
	
	/**
	 * Name of the Tapestry-IoC symbol which defines the Google Analytics API key to be used.
	 */
	public static final String GOOGLE_ANALYTICS_KEY_SYMBOL = "eloquentia.google-analytics-key";

}
