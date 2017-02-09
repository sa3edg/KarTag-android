package com.kartag.client.processing;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphFriend;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.kartag.exception.UnsupportedOperation;
import com.kartag.persistence.model.bo.PublishRequest;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;
import com.kartag.util.UserUtils;

public class FacebookProcessor extends AbstractProcessor {
	private Activity mainActivity;
	private Map<String, Trip> trips = new LinkedHashMap<String, Trip>();
	private String postedMessage = "";
	private boolean isOffersMode = false;
	private static final String PAGE_ID = "kartagapp";
	public static enum PUBLISH {ME, PAGE};
	public void process() throws Exception {

		if (request.getRequestName().equals(
				com.kartag.client.common.Request.FB_LOGIN_ORDER)) {
			login();
		} else if (request.getRequestName().equals(
				com.kartag.client.common.Request.FB_GET_FRIENDS_ORDER)) {
			getFriends();
		} else if (request
				.getRequestName()
				.equals(com.kartag.client.common.Request.FB_GET_FRIENDS_OF_FRIENDS_ORDER)) {
			getFriendsOfFriends();
		} else if (request.getRequestName().equals(
				com.kartag.client.common.Request.FB_GET_MUTUAL_FRIENDS_ORDER)) {
			getMutualFriends();
		} else if (request.getRequestName().equals(
				com.kartag.client.common.Request.FB_PUBLISH_TO_MY_WALL_ORDER)) {
			// postToMyWallWithImage();
			// publishToMyWall();
			publishTripToMyWall();
		} else if (request.getRequestName().equals(
				com.kartag.client.common.Request.FB_LOGOUT_ORDER)) {
			logOut();
		}
	}

	public void preprocess() {
		super.preprocess();
	}

	public void terminate() {
		super.terminate();
	}

	public boolean isLoggedin(Activity activity) {
		final boolean[] isLoggedIn = new boolean[1];
		// start Facebook Login
		Session.openActiveSession(activity, false,
				new Session.StatusCallback() {
					// callback when session changes state

					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (session.isOpened()) {
							isLoggedIn[0] = true;
						}
					}
				});
		return isLoggedIn[0];
	}

	private void login() throws Exception {
		// start Facebook Login

		Session.openActiveSession(mainActivity, true,
				new Session.StatusCallback() {
					// callback when session changes state
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (session.isOpened()) {

							// make request to the /me API
							Request.executeMeRequestAsync(session,
									new Request.GraphUserCallback() {
										@Override
										public void onCompleted(GraphUser user,
												Response res) {
											if (user != null) {
												User.getInstance().setProfile(
														user);
												response.setResponse(User
														.getInstance()
														.getProfile());
												getActivity()
														.postExecution(
																FacebookProcessor.this.response);

											}
										}
									});
						}
					}
				});
	}

	private void logOut() throws Exception {
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}
	}

	private void getFriends() throws Exception {

		String fqlQuery = "SELECT uid, name, mutual_friend_count FROM user WHERE uid IN "
				+ "(SELECT uid1 FROM friend WHERE uid2 = me())";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);
		Session session = Session.getActiveSession();
		Request request = new Request(session, "/fql", params, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						try {
							if (response.getError() == null) {
								Log.i("Friends Result: ", response.toString());
								User.getInstance()
										.getProfile()
										.setFriends(
												UserUtils
														.createFriendsMap(response));
								getActivity().postExecution(
										FacebookProcessor.this.response);
							} else {
								getActivity().postExecution(
										FacebookProcessor.this.response);
							}
						} catch (Exception ex) {
							try {
								throw new UnsupportedOperation();
							} catch (Exception e) {
							}
						}
					}
				});
		Request.executeBatchAsync(request);

	}

	private void getFriendsOfFriends() throws Exception {

		// String fqlQuery =
		// "SELECT uid, name, mutual_friend_count FROM user WHERE is_app_user AND uid IN "
		// +
		// "(SELECT uid2 FROM friend WHERE uid1 IN " +
		// "(SELECT uid2 FROM friend WHERE uid1 = me() ) )";
		String fqlQuery = "SELECT uid, name, mutual_friend_count FROM user WHERE uid IN "
				+ "(SELECT uid1 FROM friend WHERE uid2 IN ('612024227','552779567'))";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);
		Session session = Session.getActiveSession();
		Request request = new Request(session, "/fql", params, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						try {
							if (response.getError() == null) {
								Log.i("Friends of friends Result: ",
										response.toString());
								User.getInstance()
										.getProfile()
										.setFriendsOfFriends(
												UserUtils
														.createFriendsOfFriendsMap(response));
								getActivity().postExecution(
										FacebookProcessor.this.response);
							} else {
								getActivity().postExecution(
										FacebookProcessor.this.response);
							}
						} catch (Exception ex) {
							getActivity().postExecution(
									FacebookProcessor.this.response);
							// try
							// {
							// throw new UnsupportedOperation();
							// }
							// catch(Exception e){}
						}
					}
				});
		Request.executeBatchAsync(request);

	}

	private void getMutualFriends() throws Exception {

		User.getInstance().getProfile()
				.setFriendsOfFriends(new LinkedHashMap<String, GraphFriend>());
		Bundle params = new Bundle();
		// params.putString("fields", "id,name");
		RequestBatch requestBatch = new RequestBatch();
		for (final Trip trip : getTrips().values()) {
			requestBatch.add(new Request(Session.getActiveSession(),
					"/me/mutualfriends/" + trip.getUid(), params,
					HttpMethod.GET, new Request.Callback() {
						public void onCompleted(Response response) {
							try {
								Log.i("Result: ", response.toString());
								GraphObject graphObject = response
										.getGraphObject();
								if (graphObject != null) {
									int count = UserUtils
											.getMutualFreindsCount(response);
									if (count > 0) {
										trip.getUser().setMutual_friend_count(
												String.valueOf(count));
										User.getInstance()
												.getProfile()
												.getFriendsOfFriends()
												.put(trip.getUser().getUid(),
														trip.getUser());
										if (isOffersMode) {
											User.getInstance()
													.getFriendsOfFriendsOffers()
													.put(trip.getId(), trip);
										} else {
											User.getInstance()
													.getFriendsOfFriendsRequests()
													.put(trip.getId(), trip);
										}
									} else {
										if (isOffersMode) {
											User.getInstance()
													.getFriendsOfFriendsOffers()
													.remove(trip.getId());
										} else {
											User.getInstance()
													.getFriendsOfFriendsRequests()
													.remove(trip.getId());
										}
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}));
		}
		requestBatch.executeAsync();
	}

	public void publishTripToMyWall() {

		List<String> PERMISSIONS = Arrays.asList("publish_actions");
		Session session = Session.getActiveSession();

		if (session != null) {

			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						getMainActivity(), PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
				PublishRequest request = new PublishRequest();
				User.getInstance().getPublishQueue().put(request.getId(), request);
				return;
			}

			Bundle postParams = new Bundle();
			postParams.putString("name", "KarTag");
			postParams.putString("caption", "Trust and Share.");
			postParams.putString("description", getPostedMessage());
			postParams.putString("link",
					"https://www.facebook.com/kartagapp?fref=ts");
			postParams
					.putString(
							"picture",
							"https://m.ak.fbcdn.net/sphotos-c.ak/hphotos-ak-ash4/480164_185429044938020_636351415_n.jpg");

			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {

				}
			};

			Request request = new Request(session, "me/feed", postParams,
					HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}

	}

	public void publishTripToFBPage() {

		List<String> PERMISSIONS = Arrays.asList("publish_actions");
		Session session = Session.getActiveSession();

		if (session != null) {

			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						getMainActivity(), PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
				return;
			}

			Bundle postParams = new Bundle();
			postParams.putString("name", "KarTag");
			postParams.putString("caption", "Trust and Share.");
			postParams.putString("description", getPostedMessage());
			postParams.putString("link",
					"https://www.facebook.com/kartagapp?fref=ts");
			postParams
					.putString(
							"picture",
							"https://m.ak.fbcdn.net/sphotos-c.ak/hphotos-ak-ash4/480164_185429044938020_636351415_n.jpg");

			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {

				}
			};

			Request request = new Request(session, PAGE_ID + "/feed",
					postParams, HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}

	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	public Activity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public String getPostedMessage() {
		return postedMessage;
	}

	public void setPostedMessage(String postedMessage) {
		this.postedMessage = postedMessage;
	}

	public Map<String, Trip> getTrips() {
		return trips;
	}

	public void setTrips(Map<String, Trip> trips) {
		this.trips = trips;
	}
	
	
}
