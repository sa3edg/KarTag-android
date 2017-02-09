package com.kartag.persistence.model.bo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;


public class Pool extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String id = "";
	
	private String poolName = "";
	
	
	private List<Trip> trips = new ArrayList<Trip>();	
	
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

}
