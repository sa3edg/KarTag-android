package com.kartag.gui.presentation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.kartag.client.common.Response;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.DummyTabContent;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.actionbar.ActionBar;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;

public class HaveArideScreen extends AbstractScreen {

	public ActionBar actionBar;
	private TabHost tHost;
	private FriendsListFm friendsOffersList = null;
	private FriendOfFriendsTripsListFm friendsOfFriendsOffersList = null;
	private PoolsListFragment poolsList = null;
	private SearchTripsFm searchFragment = null;
	private CommunityTripsListFm CommunityTrips = null;

	public HaveArideScreen(FragmentActivity activity) {
		super(activity);
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.have_ride_layout);

		final String communityTitle = activity.getResources().getString(
				R.string.communityTabTitle);
		final String friendsRequestsTitle = activity.getResources().getString(
				R.string.friendsOffers);
		final String friendsOfFriendsRequestsTitle = activity.getResources()
				.getString(R.string.friendsOfFriendsOffers);
		final String poolsTitle = activity.getResources().getString(
				R.string.boolOffers);
		final String searchTitle = activity.getResources().getString(
				R.string.searchOffers);
		tHost = (TabHost) activity.findViewById(android.R.id.tabhost);
		tHost.setup();

		/**
		 * Defining Tab Change Listener event. This is invoked when tab is
		 * changed
		 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

			public void onTabChanged(String tabId) {

				android.support.v4.app.FragmentManager fm = activity
						.getSupportFragmentManager();
				CommunityTripsListFm communitysfm = (CommunityTripsListFm) fm
						.findFragmentByTag(communityTitle);

				FriendsListFm friendsOffersfm = (FriendsListFm) fm
						.findFragmentByTag(friendsRequestsTitle);

				FriendOfFriendsTripsListFm friendsOfFriendsOffersfm = (FriendOfFriendsTripsListFm) fm
						.findFragmentByTag(friendsOfFriendsRequestsTitle);

				PoolsListFragment poolsfm = (PoolsListFragment) fm
						.findFragmentByTag(poolsTitle);

				SearchTripsFm searchOffersfm = (SearchTripsFm) fm
						.findFragmentByTag(searchTitle);

				android.support.v4.app.FragmentTransaction ft = fm
						.beginTransaction();

				/** Detaches the androidfragment if exists */
				if (communitysfm != null)
					ft.detach(communitysfm);
				/** Detaches the androidfragment if exists */
				if (friendsOffersfm != null)
					ft.detach(friendsOffersfm);

				/** Detaches the applefragment if exists */
				if (friendsOfFriendsOffersfm != null)
					ft.detach(friendsOfFriendsOffersfm);

				/** Detaches the applefragment if exists */
				if (poolsfm != null)
					ft.detach(poolsfm);

				/** Detaches the applefragment if exists */
				if (searchOffersfm != null)
					ft.detach(searchOffersfm);

				/** If current tab is android */
				// tHost.getCurrentTab()
				if (tabId.equalsIgnoreCase(friendsRequestsTitle)) {
					// if(tHost.getCurrentTab() == 0) {

					if (friendsOffersfm == null) {

						if (getFriendsOffersList() == null) {
							createFriendsOffersList();
						}
						ft.add(R.id.realtabcontent, getFriendsOffersList(),
								friendsRequestsTitle);

					} else {

						ft.attach(friendsOffersfm);
					}

				} else if (tabId
						.equalsIgnoreCase(friendsOfFriendsRequestsTitle)) {

					if (friendsOfFriendsOffersfm == null) {

						if (getFriendsOfFriendsOffersList() == null) {
							createFriendsOfFriendsOffersList();
						}
						ft.add(R.id.realtabcontent,
								getFriendsOfFriendsOffersList(),
								friendsOfFriendsRequestsTitle);

					} else {

						ft.attach(friendsOfFriendsOffersfm);
					}

				} else if (tabId.equalsIgnoreCase(poolsTitle)) {

					if (poolsfm == null) {

						if (getPoolsList() == null) {
							createpoolsList();
						}
						ft.add(R.id.realtabcontent, getPoolsList(), poolsTitle);

					} else {

						ft.attach(poolsfm);
					}

				}

				else if (tabId.equalsIgnoreCase(searchTitle)) {

					if (searchOffersfm == null) {

						if (getSearchFragment() == null) {
							createSearchOffersFm();
						}
						ft.add(R.id.realtabcontent, getSearchFragment(),
								searchTitle);

					} else {

						ft.attach(searchOffersfm);
					}

				} else if (tabId.equalsIgnoreCase(communityTitle)) {

					if (communitysfm == null) {

						if (getCommunityFragment() == null) {
							createCommunityFragment();
						}
						ft.add(R.id.realtabcontent, getCommunityFragment(),
								communityTitle);

					} else {

						ft.attach(communitysfm);
					}

				}
				ft.commit();
			}

		};

		tHost.setOnTabChangedListener(tabChangeListener);

		if (User.getInstance().getSetting().getCommunityId() != null
				&& !"".equals(User.getInstance().getSetting().getCommunityId())
				&& !"0".equals(User.getInstance().getSetting().getCommunityId())
				&& !User.EMPTY_COMMUNITY.equals(User.getInstance().getSetting().getCommunityId())) {
			TabHost.TabSpec tSpecCommunityList = tHost
					.newTabSpec(communityTitle);
			tSpecCommunityList.setIndicator(communityTitle);
			tSpecCommunityList.setContent(new DummyTabContent(activity
					.getBaseContext()));
			tHost.addTab(tSpecCommunityList);
		}

		TabHost.TabSpec tSpecFriendsRequests = tHost
				.newTabSpec(friendsRequestsTitle);
		// tSpecFriendsRequests.setIndicator(friendsRequestsTitle,
		// activity.getResources()
		// .getDrawable(R.drawable.friends_req));
		tSpecFriendsRequests.setIndicator(friendsRequestsTitle);
		tSpecFriendsRequests.setContent(new DummyTabContent(activity
				.getBaseContext()));
		tHost.addTab(tSpecFriendsRequests);

		TabHost.TabSpec tSpecFriendsOfFriendsRequest = tHost
				.newTabSpec(friendsOfFriendsRequestsTitle);
		// tSpecFriendsOfFriendsRequest.setIndicator(friendsOfFriendsRequestsTitle,
		// activity.getResources()
		// .getDrawable(R.drawable.friends_of_friends_req));
		tSpecFriendsOfFriendsRequest
				.setIndicator(friendsOfFriendsRequestsTitle);
		tSpecFriendsOfFriendsRequest.setContent(new DummyTabContent(activity
				.getBaseContext()));
		tHost.addTab(tSpecFriendsOfFriendsRequest);

		TabHost.TabSpec tSpecPools = tHost.newTabSpec(poolsTitle);
		// tSpecPools.setIndicator(poolsTitle, activity.getResources()
		// .getDrawable(R.drawable.pools));
		tSpecPools.setIndicator(poolsTitle);
		tSpecPools.setContent(new DummyTabContent(activity.getBaseContext()));
		tHost.addTab(tSpecPools);

//		TabHost.TabSpec tSpecSearchRequests = tHost.newTabSpec(searchTitle);
//		// tSpecSearchRequests.setIndicator(searchTitle, activity.getResources()
//		// .getDrawable(R.drawable.search_req));
//		tSpecSearchRequests.setIndicator(searchTitle);
//		tSpecSearchRequests.setContent(new DummyTabContent(activity
//				.getBaseContext()));
//
//		// tSpecSearchRequests.setContent(new Intent(this.getActivity(),
//		// SearchTripsFm.class));
//		tHost.addTab(tSpecSearchRequests);

		ImageButton addTrip = (ImageButton) activity
				.findViewById(R.id.addTripBtn);
		addTrip.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.TRIP_FREQUENCY_SCREEN_ID);
				intent.putExtra("tripType", Trip.REQUEST);
				activity.startActivity(intent);
			}

		});
		
		ImageButton searchBtn = (ImageButton) activity
				.findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.SEARCH_ID);
				intent.putExtra("tripType", Trip.REQUEST);
				activity.startActivity(intent);
			}

		});

		ImageButton myTrips = (ImageButton) activity
				.findViewById(R.id.btnMyTrips);
		myTrips.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.MY_TRIPS_SCREEN_ID);
				intent.putExtra("tripsType", Trip.OFFER);
				activity.startActivity(intent);
			}

		});

		ImageButton inbox = (ImageButton) activity.findViewById(R.id.btnInbox);
		inbox.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.INBOX_ID);
				activity.startActivity(intent);
			}

		});
		ImageButton feedback = (ImageButton) activity
				.findViewById(R.id.btnFeedback);
		feedback.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.FEEDBACK_ID);
				activity.startActivity(intent);
			}

		});

		// ImageButton home = (ImageButton) activity.findViewById(R.id.btnHome);
		// home.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View arg0) {
		// activity.onBackPressed();
		// }
		//
		// });

		ImageButton setting = (ImageButton) activity
				.findViewById(R.id.btnSetting);
		setting.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam,
						MainActivity.SETTING_ID);
				activity.startActivity(intent);
			}

		});
		ImageButton back = (ImageButton) activity
				.findViewById(R.id.haveArideBackBtn);
		back.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				activity.onBackPressed();
			}

		});

		TextView notifications = (TextView) activity
				.findViewById(R.id.notification);
		if (User.getInstance().getNotifications() > 0) {
			notifications.setVisibility(View.VISIBLE);
			notifications.setText(String.valueOf(User.getInstance()
					.getNotifications()));
		}

	}

	@Override
	public void preExecution() {

	}

	@Override
	public void postExecution(Response response) {

	}

	public FriendsListFm getFriendsOffersList() {
		return friendsOffersList;
	}

	public void setFriendsOffersList(FriendsListFm friendsOffersList) {
		this.friendsOffersList = friendsOffersList;
	}

	public FriendOfFriendsTripsListFm getFriendsOfFriendsOffersList() {
		return friendsOfFriendsOffersList;
	}

	public void setFriendsOfFriendsOffersList(
			FriendOfFriendsTripsListFm friendsOfFriendsOffersList) {
		this.friendsOfFriendsOffersList = friendsOfFriendsOffersList;
	}

	public PoolsListFragment getPoolsList() {
		return poolsList;
	}

	public void setPoolsList(PoolsListFragment poolsList) {
		this.poolsList = poolsList;
	}

	public SearchTripsFm getSearchFragment() {
		return searchFragment;
	}

	public void setCommunityFragment(CommunityTripsListFm fm) {
		this.CommunityTrips = fm;
	}

	public CommunityTripsListFm getCommunityFragment() {
		return CommunityTrips;
	}

	private void createCommunityFragment() {
		setCommunityFragment(new CommunityTripsListFm(this, Trip.REQUEST));
	}

	public void setSearchFragment(SearchTripsFm searchFragment) {
		this.searchFragment = searchFragment;
	}

	private void createFriendsOffersList() {
		setFriendsOffersList(new FriendsListFm(this, Trip.REQUEST));
	}

	private void createFriendsOfFriendsOffersList() {
		setFriendsOfFriendsOffersList(new FriendOfFriendsTripsListFm(this,
				Trip.REQUEST));
	}

	private void createpoolsList() {
		setPoolsList(new PoolsListFragment(this, Trip.REQUEST));
	}

	private void createSearchOffersFm() {
		setSearchFragment(new SearchTripsFm(this, Trip.REQUEST));
	}

	public FragmentActivity getActivity() {
		return activity;
	}

}
