package br.com.arsmachina.eloquentia.dao.mongodb;

import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.DBRef;
import org.mongojack.JacksonDBCollection;

import br.com.arsmachina.dao.SortCriterion;
import br.com.arsmachina.eloquentia.dao.CommentDAO;
import br.com.arsmachina.eloquentia.entity.Comment;
import br.com.arsmachina.eloquentia.entity.Page;

/**
 * Implementation of {@link CommentDAO} using MongoDB.
 *  
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
public class CommentDAOImpl extends AbstractDAOImpl<Comment, String> implements CommentDAO {

	private static final SortCriterion[] DEFAULT_SORT_CRITERIA = {new SortCriterion("posted", false)};
	
	final private static PrimaryKeyEncoder<Comment, String> ID_VALUE_EXTRACTOR = new PrimaryKeyEncoder<Comment, String>() {
		public String get(Comment object) {
			return object != null ? object.getId() : null;
		}
		public void set(Comment object, String id) {
			object.setId(id);
		}
	};
	
	public CommentDAOImpl() {
		super(ID_VALUE_EXTRACTOR);
		final JacksonDBCollection<Comment, String> dbCollection = getDbCollection();
		dbCollection.ensureIndex("pageRef");
		dbCollection.ensureIndex("postedByRef");
		dbCollection.ensureIndex("parentRef");
	}
	
	@Override
	public List<Comment> findByPage(Page page) {
		final DBCursor<Comment> cursor = getDbCollection().find(DBQuery.is("pageRef", 
				new DBRef<Page, String>(page.getId(), Page.class)));
		addSortCriteria(cursor, getDefaultSortCriteria());
		return toList(cursor);
	}

	public SortCriterion[] getDefaultSortCriteria() {
		return DEFAULT_SORT_CRITERIA;
	}
	
}
