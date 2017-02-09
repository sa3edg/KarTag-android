package com.kartag.integration.facebook;

import java.util.List;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.kartag.gui.AbstractScreen;
import com.kartag.persistence.model.bo.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class FacebookConnector {

	private static FacebookConnector self = new FacebookConnector();

	private FacebookConnector() {

	}

	public static FacebookConnector getInstance() {
		return self;
	}
	
	public boolean isLoggedin(Activity activity, AbstractScreen callerScreen) {

		// start Facebook Login
		Session.openActiveSession(activity, false, new Session.StatusCallback() {
			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {
//					return true;
				}
			}
		});
		return false;
	}

	public User login(final User me, Activity activity, AbstractScreen callerScreen) {

		// start Facebook Login
		//final User me = new User();
		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
//									if (user != null) {
//										me.createUser(user);
//									}
								}
							});
				}
			}
		});
		return me;
	}

	public void logOut() {
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}
	}

	public User fetchFriendsList(final User user, Activity activity, AbstractScreen callerScreen) {

		// start Facebook Login
		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.executeMyFriendsRequestAsync(session,
							new Request.GraphUserListCallback() {

								@Override
								public void onCompleted(List<GraphUser> users,
										Response response) {
//									user.createFriends(users);
								}
							});
				}
			}
		});
		
		return user;
	}
	
	public User fetchFriendsOfFriendsList(final User user, Activity activity, AbstractScreen callerScreen) {

		String fqlQuery = "SELECT uid, name FROM user WHERE uid IN " +
	              "(SELECT uid2 FROM friend WHERE uid1 IN " +
				   "(SELECT uid2 FROM friend WHERE uid1 = me() ))";
	        Bundle params = new Bundle();
	        params.putString("q", fqlQuery);
	        Session session = Session.getActiveSession();
	        Request request = new Request(session,
	            "/fql",                         
	            params,                         
	            HttpMethod.GET,                 
	            new Request.Callback(){         
	                public void onCompleted(Response response) {
	                    Log.i("Result: " , response.toString());
	                }                  
	        }); 
	        Request.executeBatchAsync(request); 
		
		return user;
	}

}
