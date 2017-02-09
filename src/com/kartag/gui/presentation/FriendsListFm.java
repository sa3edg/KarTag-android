package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.IActivity;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.adapter.TripListImagesAdapter;
import com.kartag.performance.ImagesMemoryHandler;
import com.kartag.persistence.model.bo.Pool;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.drm.DrmStore.Action;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class FriendsListFm extends ListFragment implements IActivity {

	private List<Trip> trips = new ArrayList<Trip>();
	private int selectedIndex = -1;
	private AbstractScreen parentfm;
	private DisplayImageOptions options;
	private String tripType = "";

	public FriendsListFm(AbstractScreen main, String tripType) {
		this.parentfm = main;
		this.tripType = tripType;
		options = ImagesMemoryHandler.getDisplayImageOptions();
		if (User.getInstance().getSetting().getCommunityId() == null
				|| "".equals(User.getInstance().getSetting().getCommunityId())
				|| "0".equals(User.getInstance().getSetting().getCommunityId())
				|| User.EMPTY_COMMUNITY.equals(User.getInstance().getSetting()
						.getCommunityId())) {
			if (tripType.equals(Trip.REQUEST)) {
				OrderCoordinator.handleOrder(this,
						RequestFactory.createGetTripsRequest(Trip.REQUEST));
			} else {
				OrderCoordinator.handleOrder(this,
						RequestFactory.createGetTripsRequest(Trip.OFFER));
			}
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();
		/** Setting the multiselect choice mode for the listview */
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		if (User.getInstance().getSetting().getCommunityId() != null
				&& !"".equals(User.getInstance().getSetting().getCommunityId())
				&& !"0".equals(User.getInstance().getSetting().getCommunityId())
				&& !User.EMPTY_COMMUNITY.equals(User.getInstance().getSetting()
						.getCommunityId())) {
			showTrips();
		}
		getListView().setOnItemClickListener(
				new ListView.OnItemClickListener() {
					// @Override
					public void onItemClick(AdapterView<?> a, View v, int i,
							long l) {
						// show map here
						setSelectedIndex(i);
						Intent intent = new Intent(parentfm.getActivity(),
								MainActivity.class);
						intent.putExtra(MainActivity.nextScreenParam,
								MainActivity.TRIP_DETAILS_ID);
						intent.putExtra("trip", trips.get(i));
						parentfm.getActivity().startActivity(intent);
					}
				});

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			// @Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// edit event here

				return true;
			}
		});
	}

	public void preExecution() {
		// TODO Auto-generated method stub
	}

	private void showTrips() {
		if (tripType.equals(Trip.REQUEST)) {

			this.trips = new ArrayList<Trip>(User.getInstance()
					.getFriendsRequests().values());
		} else if (tripType.equals(Trip.OFFER)) {
			this.trips = new ArrayList<Trip>(User.getInstance()
					.getFriendsOffers().values());
		}

		TripListImagesAdapter<Trip> adapter = new TripListImagesAdapter<Trip>(
				options, parentfm.getActivity(), R.layout.trips_list_row,
				this.trips);
		setListAdapter(adapter);
		getListView().setOnScrollListener(
				new PauseOnScrollListener(ImageLoader.getInstance(), false,
						true));
		String noResult = parentfm.getActivity().getResources()
				.getString(R.string.NoResultMessage);
		setEmptyText(noResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void postExecution(Response response) {

		// TODO Auto-generated method stub

		try {
			List<Pool> pools;
			if (tripType.equals(Trip.REQUEST)) {
				pools = (ArrayList<Pool>) ResponseProcessingHelper
						.getInstance()
						.handleResponse(
								com.kartag.client.common.Request.GET_REQUESTS_REQUEST,
								(String) response.getResponse());

				User.getInstance().createMyRequestsTree(pools);
				OrderCoordinator.handleOrder(this, RequestFactory
						.createFacebookGetMutualFriendsRequest(
								parentfm.getActivity(), false));
				this.trips = new ArrayList<Trip>(User.getInstance()
						.getFriendsRequests().values());
			} else if (tripType.equals(Trip.OFFER)) {
				pools = (ArrayList<Pool>) ResponseProcessingHelper
						.getInstance()
						.handleResponse(
								com.kartag.client.common.Request.GET_OFFERS_REQUEST,
								(String) response.getResponse());

				User.getInstance().createMyOffersTree(pools);
				OrderCoordinator.handleOrder(this, RequestFactory
						.createFacebookGetMutualFriendsRequest(
								parentfm.getActivity(), true));
				this.trips = new ArrayList<Trip>(User.getInstance()
						.getFriendsOffers().values());
			}

			TripListImagesAdapter<Trip> adapter = new TripListImagesAdapter<Trip>(
					options, parentfm.getActivity(), R.layout.trips_list_row,
					this.trips);

			setListAdapter(adapter);
			getListView().setOnScrollListener(
					new PauseOnScrollListener(ImageLoader.getInstance(), false,
							true));
			String noResult = parentfm.getActivity().getResources()
					.getString(R.string.NoResultMessage);
			setEmptyText(noResult);
		} catch (Exception ex) {
			Log.e("error", Log.getStackTraceString(ex));
		}

	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
