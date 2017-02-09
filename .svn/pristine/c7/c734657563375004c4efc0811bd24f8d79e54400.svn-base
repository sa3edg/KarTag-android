package com.kartag.persistence.model.bo;

import java.util.ArrayList;
import java.util.List;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Message extends Model{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6850553253281860849L;

	private String id ;
	
	private String fromUid ;
	
	private String toUid ;
	
    private String fromName = "";
	
	private String toName = "";
	
	private String text = "";
	
	private String time ;

	private String status = "";
	
	private List<Reply> replies = new ArrayList<Reply>();
	
	public final static  String  NEW = "NEW";
	
	public final static  String  SEEN = "SEEN";
	
	public Message()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
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