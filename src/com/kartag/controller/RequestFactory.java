package com.kartag.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;

import com.facebook.model.GraphUser;
import com.kartag.client.common.Request;
import com.kartag.client.processing.FacebookProcessor;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;

public final class RequestFactory {

	private static Request createGeneralRequest(
			List<NameValuePair> nameValuePairs, String requestName) {
		Request request = new Request();
		nameValuePairs.add(new BasicNameValuePair(Request.KEY,
				Request.APP_KEY_ANDROID));
		nameValuePairs
				.add(new BasicNameValuePair(Request.AUTHENTICATE, "false"));
		request.setParameters(nameValuePairs);
		request.setOrderType(Request.SIMPLE_ORDER_TYPE);
		request.setRequestName(requestName);
		return request;
	}

	private static Request createAsyncRequest(
			List<NameValuePair> nameValuePairs, String requestName) {
		Request request = new Request();
		nameValuePairs.add(new BasicNameValuePair(Request.KEY,
				Request.APP_KEY_ANDROID));
		nameValuePairs
				.add(new BasicNameValuePair(Request.AUTHENTICATE, "false"));
		request.setParameters(nameValuePairs);
		request.setOrderType(Request.SIMPLE_ORDER_TYPE);
		request.setRequestName(requestName);
		request.setAsync(true);
		return request;
	}
	public static Request createFacebookLoginRequest(Activity mainActivity) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		request.setRequestName(Request.FB_LOGIN_ORDER);
		request.setProcessor(processor);
		return request;
	}

	public static Request createFacebookLogOutRequest(Activity mainActivity) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		request.setRequestName(Request.FB_LOGOUT_ORDER);
		request.setProcessor(processor);
		return request;
	}

	public static Request createFacebookGetFriendsRequest(Activity mainActivity) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		request.setRequestName(Request.FB_GET_FRIENDS_ORDER);
		request.setProcessor(processor);
		return request;
	}
	
	public static Request createFacebookGetMutualFriendsRequest(Activity mainActivity, boolean isOffersMode) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		if(isOffersMode)
		{
			processor.setTrips(User.getInstance().getFriendsOfFriendsOffers());
		}
		else
		{
			processor.setTrips(User.getInstance().getFriendsOfFriendsRequests());
		}
		request.setRequestName(Request.FB_GET_MUTUAL_FRIENDS_ORDER);
		request.setProcessor(processor);
		return request;
	}

	public static Request createFacebookGetFriendOfFriendsRequest(
			Activity mainActivity) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		request.setRequestName(Request.FB_GET_FRIENDS_OF_FRIENDS_ORDER);
		request.setProcessor(processor);
		return request;
	}
	public static Request createPublishToWallRequest(
			Activity mainActivity, String message) {
		Request request = new Request();
		FacebookProcessor processor = new FacebookProcessor();
		processor.setMainActivity(mainActivity);
		processor.setPostedMessage(message);
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		request.setRequestName(Request.FB_PUBLISH_TO_MY_WALL_ORDER);
		request.setProcessor(processor);
		return request;
	}

	public static Request createLoginRequest(GraphUser profile, String regId, String email, String communityId, String communityEmail) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ADD_USER_ORDER));
		nameValuePairs.add(new BasicNameValuePair("uid", profile.getId()));
		nameValuePairs.add(new BasicNameValuePair("username", profile
				.getUsername()));
		nameValuePairs.add(new BasicNameValuePair("name", profile.getName()));
		nameValuePairs.add(new BasicNameValuePair("firstname", profile
				.getFirstName()));
		nameValuePairs.add(new BasicNameValuePair("middlename", profile
				.getMiddleName()));
		nameValuePairs.add(new BasicNameValuePair("lastname", profile
				.getLastName()));
		nameValuePairs.add(new BasicNameValuePair("link", profile.getLink()));
//		nameValuePairs
//				.add(new BasicNameValuePair("gender", profile.getGender()));
		nameValuePairs.add(new BasicNameValuePair("birthday", profile
				.getBirthday()));
		nameValuePairs.add(new BasicNameValuePair("deviceId", regId));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("communityId", communityId));
		nameValuePairs.add(new BasicNameValuePair("communityEmail", communityEmail));
//		if (profile.getLocation() != null) {
//			
//			nameValuePairs.add(new BasicNameValuePair("hasLocation", "yes"));
//			nameValuePairs.add(new BasicNameValuePair("city", profile
//					.getLocation().getCity()));
//			nameValuePairs.add(new BasicNameValuePair("country", profile
//					.getLocation().getCountry()));
//			nameValuePairs.add(new BasicNameValuePair("lat", String
//					.valueOf(profile.getLocation().getLatitude())));
//			nameValuePairs.add(new BasicNameValuePair("lon", String
//					.valueOf(profile.getLocation().getLatitude())));
//			nameValuePairs.add(new BasicNameValuePair("state", profile
//					.getLocation().getState()));
//			nameValuePairs.add(new BasicNameValuePair("street", profile
//					.getLocation().getStreet()));
//			nameValuePairs.add(new BasicNameValuePair("zipcode", profile
//					.getLocation().getZip()));
//		}
//		else
//		{
			nameValuePairs.add(new BasicNameValuePair("hasLocation", "no"));
//		}
		return createGeneralRequest(nameValuePairs, Request.ADD_USER_REQUEST);
	}

	public static Request createGetTripsRequest(String type) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_TRIPS_ORDER));
		nameValuePairs.add(new BasicNameValuePair("type", type));

		return createGeneralRequest(nameValuePairs, Request.GET_OFFERS_REQUEST);
	}

	public static Request createGetUserTripRequest(String type) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_USER_TRIPS_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance()
				.getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("type", type));

		return createGeneralRequest(nameValuePairs,
				Request.GET_USER_TRIPS_REQUEST);
	}

	public static Request createAddTripRequest(String tripSchedule,
			String time, String days, Trip trip) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ADD_TRIP_ORDER));
		nameValuePairs
				.add(new BasicNameValuePair("trip_schedule", tripSchedule));
		nameValuePairs.add(new BasicNameValuePair("days", days));
		nameValuePairs.add(new BasicNameValuePair("time", time));
		nameValuePairs.add(new BasicNameValuePair("uid", trip.getUid()));
		nameValuePairs.add(new BasicNameValuePair("type", trip.getType()));
		nameValuePairs.add(new BasicNameValuePair("from", String.valueOf(trip
				.getFromId())));
		nameValuePairs.add(new BasicNameValuePair("to", String.valueOf(trip
				.getToId())));
		nameValuePairs.add(new BasicNameValuePair("availableSeats", String
				.valueOf(trip.getAvailableSeats())));
		
		nameValuePairs.add(new BasicNameValuePair("isSmokking", String
				.valueOf(trip.isSmokingAllowed())));
		nameValuePairs.add(new BasicNameValuePair("isFriendsOnly", String
				.valueOf(trip.isFriendsOnly())));
		nameValuePairs.add(new BasicNameValuePair("isWomenOnly", String
				.valueOf(trip.isWomenOnly())));
		nameValuePairs
				.add(new BasicNameValuePair("comment", trip.getComment()));
		nameValuePairs
		.add(new BasicNameValuePair("communityId", trip.getCommunityId()));

		return createGeneralRequest(nameValuePairs, Request.ADD_TRIP_REQUEST);
	}

	public static Request createJoinTripRequest(String tripId, String type) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.SEND_JOIN_TRIP_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance()
				.getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("tripId", tripId));
		nameValuePairs.add(new BasicNameValuePair("tripType", type));

		return createGeneralRequest(nameValuePairs,
				Request.SEND_JOIN_TRIP_REQUEST);
	}

	public static Request createAcceptTripRequest(String tripId, String fromUid) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ACCEPT_TRIP_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance()
				.getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("tripId", tripId));
		nameValuePairs.add(new BasicNameValuePair("fromUid", fromUid));

		return createGeneralRequest(nameValuePairs, Request.ACCEPT_TRIP_REQUEST);
	}

	public static Request createRejectTripRequest(String tripId, String fromUid) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.REJECT_TRIP_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance()
				.getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("tripId", tripId));
		nameValuePairs.add(new BasicNameValuePair("fromUid", fromUid));

		return createGeneralRequest(nameValuePairs, Request.REJECT_TRIP_REQUEST);
	}
	
	public static Request createAddFeedbackRequest(String type, String text) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ADD_FEEDBACK_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance()
				.getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("text", text));

		return createGeneralRequest(nameValuePairs, Request.ADD_FEEDBACK_REQUEST);
	}
	
	public static Request createAddMessageRequest(String fromUid, String toUid, String fromName, String toName, String text) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ADD_MESSAGE_ORDER));
		nameValuePairs.add(new BasicNameValuePair("fromUid", fromUid));
		nameValuePairs.add(new BasicNameValuePair("toUid", toUid));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("fromName", fromName));
		nameValuePairs.add(new BasicNameValuePair("toName", toName));

		return createGeneralRequest(nameValuePairs, Request.ADD_MESSAGE_REQUEST);

	}
	
	public static Request createAddReplyRequest(String messageId, String from, String to,String fromName, String toName, String text) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.ADD_REPLY_ORDER));
		nameValuePairs.add(new BasicNameValuePair("messageId", messageId));
		nameValuePairs.add(new BasicNameValuePair("fromUid", from));
		nameValuePairs.add(new BasicNameValuePair("toUid", to));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("fromName", fromName));
		nameValuePairs.add(new BasicNameValuePair("toName", toName));

		return createGeneralRequest(nameValuePairs, Request.ADD_REPLY_REQUEST);
	}
	
	public static Request createDeleteMessageRequest(String messageId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.DELETE_MESSAGE_ORDER));
		nameValuePairs.add(new BasicNameValuePair("messageId", messageId));

		return createGeneralRequest(nameValuePairs, Request.DELETE_MESSAGE_REQUEST);
	}
	
	public static Request createGetUserMessagesRequest(String userId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_USER_MESSAGES_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", userId));

		return createGeneralRequest(nameValuePairs, Request.GET_USER_MESSAGES_REQUEST);
	}
	
//	public static Request createGetUserMessagesUpdatesRequest(String userId) {
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
//				Request.GET_USER_MESSAGES_UPDATES_ORDER));
//		nameValuePairs.add(new BasicNameValuePair("userId", userId));
//
//		return createGeneralRequest(nameValuePairs, Request.GET_USER_MESSAGES_UPDATES_REQUEST);
//	}
	
	public static Request createGetUserUdatesCountRequest(String userId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_MESSAGES_NOTIFICATION_COUNT_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", userId));

		return createGeneralRequest(nameValuePairs, Request.GET_MESSAGES_NOTIFICATION_COUNT_REQUEST);
	}
	
	public static Request createGetUserNotificationsRequest(String userId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_USER_NOTIFICATION_ORDER));
		nameValuePairs.add(new BasicNameValuePair("userId", userId));

		return createGeneralRequest(nameValuePairs, Request.GET_USER_NOTIFICATION_ORDER);
	}
	
//	public static Request createGetUserNotificationsUpdatesRequest(String userId) {
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
//				Request.GET_USER_NOTIFICATION_UPDATES_ORDER));
//		nameValuePairs.add(new BasicNameValuePair("userId", userId));
//
//		return createGeneralRequest(nameValuePairs, Request.GET_USER_NOTIFICATION_UPDATES_REQUEST);
//	}
	public static Request createFilterTripsRequest(String type, String startPoolId, String endPoolId, String date) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_FILTERED_TRIPS_ORDER));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("startPoolId", startPoolId));
		nameValuePairs.add(new BasicNameValuePair("endPoolId", endPoolId));
		nameValuePairs.add(new BasicNameValuePair("tripDate", date));

		return createGeneralRequest(nameValuePairs, Request.GET_FILTERED_TRIPS_REQUEST);
	}
	
	public static Request createRateTripRequest(String tripId, String rating) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.RATE_TRIP_ORDER));
		nameValuePairs.add(new BasicNameValuePair("tripId", tripId));
		nameValuePairs.add(new BasicNameValuePair("rating", rating));

		return createGeneralRequest(nameValuePairs, Request.RATE_TRIP_REQUEST);
	}
	
	public static Request createDeleteTripRequest(String tripId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.DELETE_TRIP_ORDER));
		nameValuePairs.add(new BasicNameValuePair("tripId", tripId));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance().getProfile().getId()));

		return createGeneralRequest(nameValuePairs, Request.DELETE_TRIP_REQUEST);
	}
	
	public static Request createGetMessageReplies(String messageId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_MESSAGE_REPLIES_ORDER));
		nameValuePairs.add(new BasicNameValuePair("messageId", messageId));
		nameValuePairs.add(new BasicNameValuePair("userId", User.getInstance().getProfile().getId()));

		return createGeneralRequest(nameValuePairs, Request.GET_MESSAGE_REPLIES_REQUEST);
	}
	
	public static Request createCommunityLoginRequest(String email, String password) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.LOGIN_ORDER));
		
		nameValuePairs.add(new BasicNameValuePair("uid", User.getInstance().getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("communityEmail", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		return createGeneralRequest(nameValuePairs, Request.LOGIN_ORDER);
	}
	
	public static Request createGetCommunities(String countryId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_COMMUNITIES));
		
		nameValuePairs.add(new BasicNameValuePair("countryId", countryId));
		return createGeneralRequest(nameValuePairs, Request.GET_COMMUNITIES);
	}
	
	public static Request createForgotPasswordRequest(String email) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.FORGET_PASSWORD));
		
		nameValuePairs.add(new BasicNameValuePair("uid", User.getInstance().getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("communityEmail", email));

		return createGeneralRequest(nameValuePairs, Request.FORGET_PASSWORD);
	}
	
	public static Request createChangePasswordRequest(String oldPassword, String newPassword) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.CHANGE_PASSWORD));
		
		nameValuePairs.add(new BasicNameValuePair("uid", User.getInstance().getProfile().getId()));
		nameValuePairs.add(new BasicNameValuePair("oldPassword", oldPassword));
		nameValuePairs.add(new BasicNameValuePair("newPassword", newPassword));

		return createGeneralRequest(nameValuePairs, Request.CHANGE_PASSWORD);
	}
	
	public static Request createCommunityTripsRequest(String type) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Request.ACTION,
				Request.GET_COMMUNITY_TRIPS));
		
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("communityId", User.getInstance().getSetting().getCommunityId()));

		return createGeneralRequest(nameValuePairs, Request.GET_COMMUNITY_TRIPS);
	}

}
