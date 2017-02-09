package com.kartag.persistence.dao.bd;

import java.util.ArrayList;
import java.util.Map;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kartag.persistence.dao.BaseDao;
import com.kartag.persistence.dao.PersistenceSessionFactory;
import com.kartag.persistence.model.IModel;

public class UserDao extends BaseDao {

	public UserDao(SQLiteDatabase db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IModel[] executeQuery(IModel baseObj) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IModel> list(IModel baseObj) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean store(IModel baseObj, ArrayList<Object> insertedValues)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(IModel baseObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(IModel baseObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllRows(IModel baseObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
//	private static final String MUTUAL_FRIENDS = "mutual_friends";
//	private static final String[] MUTUAL_FRIENDS_COLUMNS = new String[] {
//			"friendsId", "mutual_friends" };
//
//	public boolean storeMutualFriends(IModel baseObj, String friendId,
//			String mutualFriends) throws SQLException {
//
//		boolean exist = selectByPrimaryKey(MUTUAL_FRIENDS,
//				MUTUAL_FRIENDS_COLUMNS, MUTUAL_FRIENDS_COLUMNS[0], friendId) == null ? false
//				: true;
//		if (!exist) {
//			database.execSQL("create table if not exists " + MUTUAL_FRIENDS
//					+ "( " + MUTUAL_FRIENDS_COLUMNS[0] + " TEXT primary key, "
//					+ MUTUAL_FRIENDS_COLUMNS[1] + " TEXT not null);");
//			ContentValues values = new ContentValues();
//
//			values.put("map_key", friendId);
//			values.put("map_value", mutualFriends);
//			long insertId = database.insert(baseObj.getTableName(), null,
//					values);
//			if (insertId > 0)
//				return true;
//		}
//		else
//		{
//			return true;
//		}
//		return false;
//	}
//	
//  public ArrayList<IModel> list(IModel baseObj) throws SQLException {
//		
//		ArrayList<IModel> rows = new ArrayList<IModel>();
//		try
//		{
//           database = PersistenceSessionFactory.currentSession();
//	       cursor = database.query(baseObj.getTableName(),
//	    		   baseObj.getColumns(), null, null, null, null, null);
//
//	       cursor.moveToFirst();
//	       while (!cursor.isAfterLast()) {
//	    	
////	    	   rows.add(DaoUtil.createRow(baseObj, cursor));
//	           cursor.moveToNext();
//	    }
//	    
//		}
//		finally
//		{
//			close();
//		}
//	    return rows;
//	}

	@Override
	public IModel get(IModel baseObj, Object pk) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
