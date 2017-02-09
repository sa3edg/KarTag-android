package com.kartag.persistence.dao;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao implements IDefaultDao{
	protected SQLiteDatabase database;
	public BaseDao(SQLiteDatabase db)
	{
		this.database = db;
	}
	public void close() throws SQLException
	{
		if(database != null)
			database.close();
//		if(cursor != null)
//			cursor.close();
	}

}
