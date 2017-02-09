package com.kartag.client.common;

public interface RequestActions {
	
	//KarTag orders
	public static final String GET_TRIPS_ORDER = "getTrips";
	public static final String GET_USER_TRIPS_ORDER = "getUserTrips";
	public static final String ADD_TRIP_ORDER = "addTrip";
	public static final String SEND_JOIN_TRIP_ORDER = "joinTrip";
	public static final String ACCEPT_TRIP_ORDER = "acceptTrip";
	public static final String REJECT_TRIP_ORDER = "rejectTrip";
	public static final String ADD_USER_ORDER = "addUser";
	public static final String ADD_FEEDBACK_ORDER = "addFeedback";
	public static final String ADD_MESSAGE_ORDER = "addMessage";
	public static final String ADD_REPLY_ORDER = "addReply";
	public static final String DELETE_MESSAGE_ORDER = "deleteMessage";
	public static final String GET_USER_MESSAGES_ORDER = "getMessages";
//	public static final String GET_USER_MESSAGES_UPDATES_ORDER = "getMessagesUpdates";
	public static final String GET_MESSAGES_NOTIFICATION_COUNT_ORDER = "getMessagesNotificationCount";
	public static final String GET_USER_NOTIFICATION_ORDER = "getNotification";
//	public static final String GET_USER_NOTIFICATION_UPDATES_ORDER = "addNotificationUpdates";
	public static final String GET_FILTERED_TRIPS_ORDER = "getFilteredTrips";
	public static final String RATE_TRIP_ORDER = "rateTrip";
	public static final String DELETE_TRIP_ORDER = "deleteTrip";
	public static final String GET_MESSAGE_REPLIES_ORDER = "getMessageReplies";
	public static final String GET_COMMUNITY_TRIPS = "getCommunityTrips";
	public static final String LOGIN_ORDER = "loginUser";
	public static final String FORGET_PASSWORD = "forgetPassword";
	public static final String CHANGE_PASSWORD = "changePassword";
	public static final String GET_COMMUNITIES = "getCommunities";


}
