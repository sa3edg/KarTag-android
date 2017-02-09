package com.kartag.persistence.model;

import java.io.Serializable;

import com.kartag.persistence.dao.IDefaultDao;

public interface IModel extends Serializable{
	
	public String getTableName();
	public String[] getColumns();
	public String getId();
	public IDefaultDao getDao();	

}
