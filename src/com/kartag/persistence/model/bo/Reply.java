package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Reply extends Model{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5632540804543416524L;

	private String id ;
	
	private String messageId ;
	
	private String fromUid ;
	
	private String toUid ;
	
    private String fromName = "";
	
	private String toName = "";
	
	private String text = "";
	
	private String time ;

	private String status = "";
	
	public final static  String  NEW = "NEW";
	
	public final static  String  SEEN = "SEEN";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	public IDefaultDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
}
