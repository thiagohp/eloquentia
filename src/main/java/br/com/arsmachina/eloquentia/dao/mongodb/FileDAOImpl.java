package br.com.arsmachina.eloquentia.dao.mongodb;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.dao.FileDAO;
import br.com.arsmachina.eloquentia.entity.File;

/**
 * Implementation of {@link FileDAO} using MongoDB.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class FileDAOImpl extends AbstractDAOImpl<File, String> implements FileDAO {

	private static final SortCriterion[] DEFAULT_SORT_CRITERIA = {new SortCriterion("name", true)};
	
	final private static PrimaryKeyEncoder<File, String> ID_VALUE_EXTRACTOR = new PrimaryKeyEncoder<File, String>() {
		public String get(File object) {
			return object != null ? object.getId() : null;
		}
		public void set(File object, String id) {
			object.setId(id);
		}
	};
	
	public FileDAOImpl() {
		super(ID_VALUE_EXTRACTOR);
		final JacksonDBCollection<File, String> dbCollection = getDbCollection();
		dbCollection.ensureIndex("tags");
		try {
			dbCollection.ensureIndex(new BasicDBObject("name", 1), "name", true);
		}
		catch (MongoException e) {
			LoggerFactory.getLogger(FileDAOImpl.class).error("Exception while creating name index", e);
		}
	}

	public File findByName(String name) {
		DBCursor<File> cursor = getDbCollection().find(DBQuery.is("name", name)).limit(1);
		return cursor.hasNext() ? cursor.next() : null;
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return DEFAULT_SORT_CRITERIA;
	}
	
}
