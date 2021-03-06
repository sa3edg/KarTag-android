package com.kartag.persistence.model.bo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.facebook.model.GraphFriend;
import com.facebook.model.GraphUser;
import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Trip extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private String uid = "";
	
	private String time = "";

	private String type = "";

	private int fromId;

	private int toId;

	private int availableSeats;

	private int userCount;

	private boolean smokingAllowed = false;

	private boolean friendsOnly = false;

	private boolean womenOnly = false;
	

	private String status = "";

	private String comment = "";

	private Pool startPool;
	
	private Pool endPool;
	
	private GraphFriend user;
	
	private int rate;
	
	private String communityId = "";

	private Map<String, GraphFriend> users = new LinkedHashMap<String, GraphFriend>();

	private Map<String, Pool> pools = new LinkedHashMap<String, Pool>();

	public static final String REQUEST = "REQUEST";

	public static final String OFFER = "OFFER";
	
    public static final String REQUEST_SENT = "REQUEST_SENT";
	
	public static final String ACCEPTED = "ACCEPTED";
	
	public static final String REJECTED = "REJECTED";
	
	public static final String JOIN_REQUEST_ALREADY_EXIST = "REQUEST_EXIST";
	
	public static final String JOIN_REQUEST_ALREADY_ACCEPTED = "REQUEST_ACCEPTED";

	public static final String TRIP_OWNER = "OWNER";

	public static final String CANCELED = "CANCELED";

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

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public boolean isSmokingAllowed() {
		return smokingAllowed;
	}

	public void setSmokingAllowed(boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public boolean isFriendsOnly() {
		return friendsOnly;
	}

	public void setFriendsOnly(boolean friendsOnly) {
		this.friendsOnly = friendsOnly;
	}

	public boolean isWomenOnly() {
		return womenOnly;
	}

	public void setWomenOnly(boolean womenOnly) {
		this.womenOnly = womenOnly;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Pool getStartPool() {
		return startPool;
	}

	public void setStartPool(Pool startPool) {
		this.startPool = startPool;
	}

	public Pool getEndPool() {
		return endPool;
	}

	public void setEndPool(Pool endPool) {
		this.endPool = endPool;
	}


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public GraphFriend getUser() {
		return user;
	}

	public void setUser(GraphFriend user) {
		this.user = user;
	}
	public void setUser(GraphUser user) {
		GraphFriend self = new GraphFriend();
		self.setUid(user.getId());
		self.setName(user.getName());
		self.setUserName(user.getUsername());
		this.user = self;
	}


	public Map<String, GraphFriend> getUsers() {
		return users;
	}

	public void setUsers(Map<String, GraphFriend> users) {
		this.users = users;
	}

	public Map<String, Pool> getPools() {
		return pools;
	}

	public void setPools(Map<String, Pool> pools) {
		this.pools = pools;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}


}
