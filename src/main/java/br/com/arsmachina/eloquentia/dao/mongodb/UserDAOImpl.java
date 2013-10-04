package br.com.arsmachina.eloquentia.dao.mongodb;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.dao.UserDAO;
import br.com.arsmachina.eloquentia.entity.User;

/**
 * Implementation of {@link UserDAO} using MongoDB.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class UserDAOImpl extends AbstractDAOImpl<User, String> implements UserDAO {

	private static final SortCriterion[] DEFAULT_SORT_CRITERIA = {new SortCriterion("name", true)};
	
	final private static PrimaryKeyEncoder<User, String> ID_VALUE_EXTRACTOR = new PrimaryKeyEncoder<User, String>() {
		public String get(User object) {
			return object != null ? object.getId() : null;
		}
		public void set(User object, String id) {
			object.setId(id);
		}
	};
	
	public UserDAOImpl() {
		super(ID_VALUE_EXTRACTOR);
		final JacksonDBCollection<User, String> dbCollection = getDbCollection();
		dbCollection.ensureIndex("tags");
		try {
			dbCollection.ensureIndex(new BasicDBObject("username", 1), "username", true);
		}
		catch (MongoException e) {
			LoggerFactory.getLogger(UserDAOImpl.class).error("Exception while creating username index", e);
		}
	}

	public User findByLogin(String username) {
		DBCursor<User> cursor = getDbCollection().find(DBQuery.is("login", username)).limit(1);
		return cursor.hasNext() ? cursor.next() : null;
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return DEFAULT_SORT_CRITERIA;
	}
	
}
