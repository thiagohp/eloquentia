package br.com.arsmachina.eloquentia.dao.mongodb;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.MongoCollection;
import org.mongojack.MongoJsonMappingException;
import org.mongojack.WriteResult;
import org.mongojack.internal.MongoJackModule;
import org.mongojack.internal.object.BsonObjectGenerator;

import br.com.arsmachina.dao.DAO;
import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.entity.Page;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * Abstract DAO implementation.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public abstract class AbstractDAOImpl<T, K extends Serializable> implements DAO<T, K> {
	
	final private static DB DB;
	
	final private JacksonDBCollection<T, K> dbCollection;
	
	final private Class<T> entityClass;
	
	final private Class<K> idClass;
	
	final private PrimaryKeyEncoder<T, K> primaryKeyEncoder;
	
	final private ObjectMapper objectMapper = MongoJackModule.configure(new ObjectMapper());
	
	static {
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			throw new ExceptionInInitializerError(e);
		}
		DB = mongoClient.getDB("eloquentia");
	}
	
	/**
	 * Single constructor of this class.
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAOImpl(PrimaryKeyEncoder<T, K> idValueExtractor) {
		this.primaryKeyEncoder = idValueExtractor;
		final Type genericSuperclass = getClass().getGenericSuperclass();
		final ParameterizedType parameterizedType = ((ParameterizedType) genericSuperclass);
		entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		idClass = (Class<K>) parameterizedType.getActualTypeArguments()[1];
		MongoCollection mongoCollection = entityClass.getAnnotation(MongoCollection.class);
		String collection = entityClass.getSimpleName().toLowerCase();
		if (mongoCollection != null) {
			collection = mongoCollection.name();
		}
		dbCollection = JacksonDBCollection.wrap(DB.getCollection(collection), entityClass, idClass);
	}
	
	public void delete(T object) {
		delete(primaryKeyEncoder.get(object));
	}

	public void delete(K id) {
		dbCollection.removeById(id);
	}

	public void save(T object) {
		if (isPersistent(object)) {
			update(object);
		}
		else {
			final WriteResult<T, K> writeResult = dbCollection.insert(object);
			// FIXME: it seems insert() returns n=0 even when the write is successful. Investigate.
			// assertNumberOfChanges(writeResult);
			primaryKeyEncoder.set(object, writeResult.getSavedId());
		}
	}

	public T update(T object) {
		
		if (!isPersistent(object)) {
			throw new RuntimeException("Object is not persistent for update: " + object);
		}
		
		K id = primaryKeyEncoder.get(object);
		final WriteResult<T, K> writeResult = dbCollection.updateById(id, object);
		assertNumberOfChanges(writeResult);

		return object;
		
	}

	protected void assertNumberOfChanges(final WriteResult<T, K> writeResult) {
		final int n = writeResult.getN();
		if (n != 1) {
			throw new MongoException(String.format("AbstractDAOImpl.update() changed %d records instead of 1", n));
		}
	}

	/**
	 * Does nothing, as MongoDB doesn't have any persistence context or attached state.
	 */
	public void evict(T object) {
	}

	public boolean isPersistent(T object) {
		K id = primaryKeyEncoder.get(object);
		return id != null;
	}

	public long countAll() {
		return dbCollection.count();
	}

	public T findById(K id) {
		return dbCollection.findOneById(id);
	}

	public List<T> findByIds(K... ids) {
		DBCursor<T> cursor = dbCollection.find(DBQuery.in("_id", (Object[]) ids));
		return toList(cursor);
	}

	/**
	 * Returns a {@link List} containing the objects returned in a {@link DBCursor}.
	 * @param cursor a {@link DBCursor}.
	 * @return a {@link List}.
	 */
	protected List<T> toList(DBCursor<T> cursor) {
		List<T> list = new ArrayList<T>();
		for (T object : cursor) {
			list.add(object);
		}
		return list;
	}

	public List<T> findAll() {
		final DBCursor<T> cursor = dbCollection.find();
		addSortCriteria(cursor, getDefaultSortCriteria());
		return toList(cursor);
	}

	public List<T> findByExample(T example) {
		final DBCursor<T> cursor = dbCollection.find(convertToBasicDbObject(example));
		addSortCriteria(cursor, getDefaultSortCriteria());
		return toList(cursor);
	}

	public T refresh(T object) {
		return findById(primaryKeyEncoder.get(object));
	}

	/**
	 * Just returns the object unchanged, as MongoDB doesn't have anything similar to the
	 * attached state of JPA.
	 */
	public T reattach(T object) {
		return object;
	}

	public List<T> findAll(int firstResult, int maxResults, SortCriterion... sortCriteria) {
		final DBCursor<T> cursor = dbCollection.find().skip(firstResult).limit(maxResults);
		addSortCriteria(cursor, sortCriteria);
		return toList(cursor);
	}

	/**
	 * Adds sort criteria to a {@link DBCursor}.
	 * @param cursor a {@link DBCursor}.
	 * @param sortingCriteria an array of {@link SortCriterion}.
	 */
	protected void addSortCriteria(final DBCursor<T> cursor, SortCriterion... sortingCriteria) {
		if (sortingCriteria != null && sortingCriteria.length > 0) {
			BasicDBObject orderBy = new BasicDBObject();
			for (SortCriterion sortCriterion : sortingCriteria) {
				orderBy.append(sortCriterion.getProperty(), sortCriterion.isAscending() ? 1 : -1);
			}
			cursor.sort(orderBy);
		}
	}
	
	/**
	 * Adds skip and limit to a {@link DBCursor}.
	 * @param cursor a {@link DBCursor}.
	 * @param firstResult an <code>int</code> containg the number of results to be skipped. 
	 * @param maxResults an <code>int</code> containg the maximum number of results to be returned.
	 */
	protected void addSkipAndLimit(DBCursor<Page> cursor, int firstResult, int maxResults) {
		cursor.skip(firstResult).limit(maxResults);
	}

	/**
	 * Returns the {@link JacksonDBCollection} for this entity class.
	 * @return a {@link JacksonDBCollection}.
	 */
	protected JacksonDBCollection<T, K> getDbCollection() {
		return dbCollection;
	}

	/**
	 * Copied from {@link JacksonDBCollection#convertToBasicDbObject(T)}
	 * @param object
	 * @return
	 * @throws MongoException
	 */
	protected DBObject convertToBasicDbObject(T object) throws MongoException {
        if (object == null) {
            return null;
        }
        BsonObjectGenerator generator = new BsonObjectGenerator();
        try {
            objectMapper.writeValue(generator, object);
        } catch (JsonMappingException e) {
            throw new MongoJsonMappingException(e);
        } catch (IOException e) {
            // This shouldn't happen
            throw new MongoException("Unknown error occurred converting BSON to object", e);
        }
        return generator.getDBObject();
    }
	
	/**
	 * Extracts the object from a {@link DBCursor} representing a search with 0 or 1 results.
	 * If there are no results, <code>null</code> is returned.
	 * 
	 * @param cursor a {@link DBCursor}.
	 * @return a <code>T</code> instance or <code>null</code>.
	 */
	protected T objectOrNull(DBCursor<T> cursor) {
		return cursor.hasNext() ? cursor.next() : null;
	}

}
