package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Country extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name = "";

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

		public void setId(int id) {
		this.id = id;
	}

	public String getId() {
		return ""+id;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDefaultDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
