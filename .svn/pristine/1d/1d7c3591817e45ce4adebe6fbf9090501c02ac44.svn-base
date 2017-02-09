package com.kartag.persistence.dao;

import java.util.ArrayList;

import android.database.SQLException;

import com.kartag.persistence.model.IModel;

public interface IDefaultDao {
	/**
	    * execute sql query to fetch the stored data
	    * @param baseObj the base object
	    * @return
	    */
	   public IModel[] executeQuery(IModel baseObj)throws SQLException;
	   
	   /**
	    * execute sql query to fetch the stored data
	    * @param baseObj the base object
	    * @return
	    */
	   public IModel get(IModel baseObj, Object pk)throws SQLException;
	   /**
	    * list all stored rows
	    * @param baseObj the base object
	    * @return
	    */
	   public ArrayList<IModel> list(IModel baseObj)throws SQLException;
	   
	   
	   /**
	    * store object into data base
	    * @param baseObj the base object
	    * @return
	    */
	   public boolean store(IModel baseObj, ArrayList<Object> insertedValues)throws SQLException;

	   /**
	    * execute delete query
	    * @param baseObj the base object
	    * @return
	    */
	   public boolean delete(IModel baseObj)throws SQLException;

	   /**
	    * execute update query
	    * @param baseObj the base object
	    * @return
	    */
	   public boolean update(IModel baseObj)throws SQLException;
	     

	   /**
	    * execute delete all query
	    * @param baseObj the base object
	    * @return
	    */
	   public boolean deleteAllRows(IModel baseObj) throws SQLException;

}
