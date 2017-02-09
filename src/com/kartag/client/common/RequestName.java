package com.kartag.client.common;

public interface RequestName {

	//facebook requests names.
	public static final String FB_LOGIN_ORDER = "LOGIN";
	public static final String FB_LOGOUT_ORDER = "LOGOUT";
	public static final String FB_GET_FRIENDS_ORDER = "FRIENDS";
	public static final String FB_GET_FRIENDS_OF_FRIENDS_ORDER = "FRIENDS_OF_FRIENDS";
	public static final String FB_GET_MUTUAL_FRIENDS_ORDER = "MUTUAL_FRIENDS";
	public static final String FB_PUBLISH_TO_MY_WALL_ORDER = "PUBLISH_TO_WALL";
	
	//kartag requests names.
	public static final String GET_OFFERS_REQUEST = "GET_OFFERS";
	public static final String GET_REQUESTS_REQUEST = "GET_REQUESTS";
	public static final String GET_USER_TRIPS_REQUEST = "GET_USER_TRIPS";
	public static final String ADD_TRIP_REQUEST = "ADD_TRIP";
	public static final String SEND_JOIN_TRIP_REQUEST = "JOIN_TRIP";
	public static final String ACCEPT_TRIP_REQUEST = "ACCEPT_TRIP";
	public static final String REJECT_TRIP_REQUEST = "REJECT_TRIP";
	public static final String ADD_USER_REQUEST = "ADD_USER";
	public static final String ADD_FEEDBACK_REQUEST = "ADD_FEEDBACK";
	public static final String ADD_MESSAGE_REQUEST = "ADD_MESSAGE";
	public static final String ADD_REPLY_REQUEST = "ADD_REPLY";
	public static final String DELETE_MESSAGE_REQUEST = "DELETE_MESSAGE";
	public static final String GET_USER_MESSAGES_REQUEST = "GET_MESSAGES";
	public static final String GET_USER_MESSAGES_UPDATES_REQUEST = "GET_NEW_MESSAGES";
	public static final String GET_MESSAGES_NOTIFICATION_COUNT_REQUEST = "GET_UPDATES_COUNT";
	public static final String GET_USER_NOTIFICATION_REQUEST = "GET_NOTIFICATION";
	public static final String GET_USER_NOTIFICATION_UPDATES_REQUEST = "GET_NEW_NOTIFICATION";
	public static final String GET_FILTERED_TRIPS_REQUEST = "FILTER_TRIPS";
	public static final String RATE_TRIP_REQUEST = "RATE_TRIPS";
	public static final String DELETE_TRIP_REQUEST = "DELETE_TRIPS";
	public static final String GET_MESSAGE_REPLIES_REQUEST = "GET_MESSAGE_REPLIES";

}
