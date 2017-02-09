package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class City extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int countryId;
	
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

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDefaultDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}
}
