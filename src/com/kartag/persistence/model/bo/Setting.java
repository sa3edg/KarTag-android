package com.kartag.persistence.model.bo;

import com.kartag.persistence.dao.IDefaultDao;
import com.kartag.persistence.model.Model;

public class Setting extends Model{

	public static final String ON = "on";
	public static final String OFF = "off";
	private String publish = "on";
	private String logout = "off";
	private String communityId = "";
//	private String liked = "off";
//	private String followed = "off";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1011232379302975282L;

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

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

//	public String getLiked() {
//		return liked;
//	}
//
//	public void setLiked(String liked) {
//		this.liked = liked;
//	}
//
//	public String getFollowed() {
//		return followed;
//	}
//
//	public void setFollowed(String followed) {
//		this.followed = followed;
//	}

}
