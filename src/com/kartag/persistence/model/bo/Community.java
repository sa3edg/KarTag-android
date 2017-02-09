package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;


public class Community extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String communityName = "";
	
	private int countryId;
	
	

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCommunityName() {
		return communityName;
	}


	@Override
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDefaultDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
