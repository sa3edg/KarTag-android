package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import com.kartag.client.common.Request;
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
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class CommunityTripsListFm extends ListFragment implements IActivity {

	private List<Trip> trips = new ArrayList<Trip>();
	private int selectedIndex = -1;
	private AbstractScreen parentfm;
	private DisplayImageOptions options;
	private String tripType = "";

	public CommunityTripsListFm(AbstractScreen main, String tripType) {
		this.parentfm = main;
		this.tripType = tripType;
		options = ImagesMemoryHandler.getDisplayImageOptions();
		if (tripType.equals(Trip.REQUEST)) {
			OrderCoordinator.handleOrder(this,
					RequestFactory.createGetTripsRequest(Trip.REQUEST));
		} else {
			OrderCoordinator.handleOrder(this,
					RequestFactory.createGetTripsRequest(Trip.OFFER));
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

	@Override
	@SuppressWarnings("unchecked")
	public void postExecution(Response response) {

		// TODO Auto-generated method stub
		try {
			if (response.getRequestName().equals(Request.GET_COMMUNITY_TRIPS)) {
				this.trips = (ArrayList<Trip>) ResponseProcessingHelper
						.getInstance()
						.handleResponse(
								com.kartag.client.common.Request.GET_COMMUNITY_TRIPS,
								(String) response.getResponse());

				for (Trip trip : trips) {

					if (tripType.equals(Trip.OFFER)) {
						Pool startPool = User.getInstance().getOffersPools()
								.get(String.valueOf(trip.getFromId()));
						Pool endPool = User.getInstance().getOffersPools()
								.get(String.valueOf(trip.getToId()));
						trip.setStartPool(startPool);
						trip.setEndPool(endPool);
					} else if (tripType.equals(Trip.REQUEST)) {
						Pool startPool = User.getInstance().getRequestsPools()
								.get(String.valueOf(trip.getFromId()));
						Pool endPool = User.getInstance().getRequestsPools()
								.get(String.valueOf(trip.getToId()));
						trip.setStartPool(startPool);
						trip.setEndPool(endPool);
					}
					if (trip.getUid().equals(
							User.getInstance().getProfile().getId())) {
						trips.remove(trip);
					}

				}

				TripListImagesAdapter<Trip> adapter = new TripListImagesAdapter<Trip>(
						options, parentfm.getActivity(),
						R.layout.trips_list_row, this.trips);
				setListAdapter(adapter);
				getListView().setOnScrollListener(
						new PauseOnScrollListener(ImageLoader.getInstance(),
								false, true));
				String noResult = parentfm.getActivity().getResources()
						.getString(R.string.NoResultMessage);
				setEmptyText(noResult);
			} else if (response.getRequestName().equals(
					Request.GET_REQUESTS_REQUEST)
					|| response.getRequestName().equals(
							Request.GET_OFFERS_REQUEST)) {
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

				}

				if (tripType.equals(Trip.REQUEST)) {
					OrderCoordinator.handleOrder(this, RequestFactory
							.createCommunityTripsRequest(Trip.REQUEST));
				} else {
					OrderCoordinator.handleOrder(this, RequestFactory
							.createCommunityTripsRequest(Trip.OFFER));
				}
			}
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
