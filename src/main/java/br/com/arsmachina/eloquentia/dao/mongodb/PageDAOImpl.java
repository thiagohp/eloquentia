package br.com.arsmachina.eloquentia.dao.mongodb;

import java.util.Date;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.DBQuery.Query;
import org.mongojack.JacksonDBCollection;

import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.dao.PageDAO;
import br.com.arsmachina.eloquentia.entity.Page;
import br.com.arsmachina.eloquentia.entity.Tag;

import com.mongodb.BasicDBObject;

/**
 * Implementation of {@link PageDAO} using MongoDB and MongoJack.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class PageDAOImpl extends AbstractDAOImpl<Page, String> implements PageDAO {

	final private static PrimaryKeyEncoder<Page, String> ID_VALUE_EXTRACTOR = new PrimaryKeyEncoder<Page, String>() {
		public String get(Page object) {
			return object != null ? object.getId() : null;
		}
		public void set(Page object, String id) {
			object.setId(id);
		}
	};
	
	public PageDAOImpl() {
		super(ID_VALUE_EXTRACTOR);
		final JacksonDBCollection<Page, String> dbCollection = getDbCollection();
		dbCollection.ensureIndex(new BasicDBObject("uri", 1), "uri", true);
		dbCollection.ensureIndex("tags");
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return PageDAO.DEFAULT_SORT_CRITERIA;
	}

	public Page findByUri(String uri) {
		return objectOrNull(getDbCollection().find(getUriQuery(uri)));
	}
	
	protected Query getUriQuery(String uri) {
		return DBQuery.is("uri", uri);
	}

	public List<Page> findByTag(Tag tag, int firstResult, int maxResults, SortCriterion... sortCriteria) {
		return findByTag(tag.getName(), firstResult, maxResults, sortCriteria);
	}

	public List<Page> findByTag(final String tagName, int firstResult, int maxResults, SortCriterion... sortCriteria) {
		DBCursor<Page> cursor = getDbCollection().find().in("tags", tagName).lessThan("posted", new Date());
		addSortCriteria(cursor, sortCriteria);
		addSkipAndLimit(cursor, firstResult, maxResults);
		return toList(cursor);
	}

	public boolean exists(String uri) {
		return getDbCollection().getCount(getUriQuery(uri)) > 0;
	}

	public boolean isTagReferenced(String name) {
		return getDbCollection().find().in("tags", name).limit(1).hasNext();
	}

}
