package br.com.arsmachina.eloquentia.dao.mongodb;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;

import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.dao.TagDAO;
import br.com.arsmachina.eloquentia.entity.Tag;

/**
 * Implementation of {@link TagDAO} using MongoDB and MongoJack.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class TagDAOImpl extends AbstractDAOImpl<Tag, String> implements TagDAO {

	private static final SortCriterion[] DEFAULT_SORT_CRITERIA = {};
	
	final private static PrimaryKeyEncoder<Tag, String> ID_VALUE_EXTRACTOR = new PrimaryKeyEncoder<Tag, String>() {
		public String get(Tag object) {
			return object != null ? object.getId() : null;
		}
		public void set(Tag object, String id) {
			object.setId(id);
		}
	};
	
	public TagDAOImpl() {
		super(ID_VALUE_EXTRACTOR);
		final JacksonDBCollection<Tag, String> dbCollection = getDbCollection();
		dbCollection.ensureIndex(new BasicDBObject("name", 1), "name", true);
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return DEFAULT_SORT_CRITERIA;
	}

	public Tag findByName(String name) {
		DBCursor<Tag> cursor = getDbCollection().find(DBQuery.is("name", name));
		return cursor.hasNext() ? cursor.next() : null;
	} 

}
