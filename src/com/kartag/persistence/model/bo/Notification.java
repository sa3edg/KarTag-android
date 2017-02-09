package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Notification extends Model{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5958315493667293516L;

	private String id;
	
	private String tripId ;
	
	private String fromUid ;
	
	private String toUid ;
	
	private String fromName = "";
	
	private String fromPoolName = "";
	
	private String toPoolName = "";
	
	private String tripTime ;
	
	private String notificationTime ;

	private String type = "";
	
	private String status = "";
	
	//notification type used for join to trip request.
    public final static  String  JOIN_REQUEST_TYPE = "JOIN_REQUEST_TYPE";
		
	
	//notification type used for accept or reject trip.
	public final static  String  REPLY_REQUEST_TYPE = "REPLY_REQUEST_TYPE";
	
	public final static  String  JOIN_REQUEST = "JOIN_REQUEST";
	
	public final static  String  ACCEPT_REQUEST = "ACCEPTED_STATUS";
	
	public final static  String  REJECT_REQUEST = "REJECTED_STATUS";
	
	public final static  String  CANCELED_STATUS = "CANCELED_STATUS";

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDefaultDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getFromUid() {
		return fromUid;
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	public String getToUid() {
		return toUid;
	}

	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromPoolName() {
		return fromPoolName;
	}

	public void setFromPoolName(String fromPoolName) {
		this.fromPoolName = fromPoolName;
	}

	public String getToPoolName() {
		return toPoolName;
	}

	public void setToPoolName(String toPoolName) {
		this.toPoolName = toPoolName;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public String getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
