package com.kartag.persistence.dao;

import com.kartag.exception.BadRuntimeException;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceSessionFactory extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "carshare.db";
	
	private static final int DATABASE_VERSION = 1;
	
	private static SQLiteDatabase database;
	private static PersistenceSessionFactory seif = null;
	
	private PersistenceSessionFactory(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public static PersistenceSessionFactory createSessionInstance(Context context) 
	   {
		   try
	   	   {
	   	      if(seif == null)
	   	      {
	   		      seif = new PersistenceSessionFactory(context);
	   	      }
	   	   }
	   	   catch(Exception ex)
	   	   {
	          throw new BadRuntimeException(ex);
	   	   }
		   return seif;
	   }
	
    public synchronized SQLiteDatabase currentSession() throws SQLException {
        
        if (database == null) 
        {
        	throw new SQLException();
         }
        return database;
    }

}
