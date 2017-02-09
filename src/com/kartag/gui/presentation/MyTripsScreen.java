package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;


import com.kartag.client.common.Request;
import com.kartag.client.common.RequestName;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.adapter.MyTripListImagesAdapter;
import com.kartag.performance.ImagesMemoryHandler;
import com.kartag.persistence.model.bo.Pool;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MyTripsScreen extends AbstractScreen {

	ListView listView1;
	private DisplayImageOptions options;
    private String tripsType = "";
	private List<Trip> trips = new ArrayList<Trip>();
	private MyTripListImagesAdapter<Trip> adapter ;
	public MyTripsScreen(FragmentActivity activity, String tripsType) {
		super(activity);
		this.tripsType = tripsType;
	}

	public void loadScreen() {
		drawView();

	}

	private void drawView() {
//		Log.i("response > ", (String) response.getResponse());
		try {

			activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
			activity.setContentView(R.layout.my_trips_layout);
			listView1 = (ListView) activity.findViewById(R.id.myTripsListView);
			
			listView1.setDescendantFocusability(
					ListView.FOCUS_BLOCK_DESCENDANTS);
			listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			ImageButton back = (ImageButton) activity.findViewById(R.id.myTripBackBtn);
			back.setOnClickListener(new OnClickListener() {
	        	 
				public void onClick(View arg0) {
					activity.onBackPressed();
				}
	 
			});

			
			options = ImagesMemoryHandler.getDisplayImageOptions();
			

//			View header = (View) activity.getLayoutInflater().inflate(
//					R.layout.my_trips_layout, null);

//			actionBar = (ActionBar) activity.findViewById(R.id.actionbar);
//			Display display = activity.getWindowManager().getDefaultDisplay();
//			Point size = new Point();
//			display.getSize(size);
//			int screenWidth = size.x;
//			View layout = (LinearLayout) activity.findViewById(R.id.myTripActionBarLayout);
//			int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, activity.getResources().getDisplayMetrics());
//			
//			layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth - (width*3), width)); 
//			actionBar.addAction(new AddAction());
//			actionBar.addAction(new RefreshAction());
//			actionBar.addAction(new DeleteAction());
			//listView1.addHeaderView(header);
		
			ImageButton addTrip = (ImageButton) activity
					.findViewById(R.id.addTrip);
			addTrip.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					if(tripsType.equals(Trip.REQUEST))
					{
						tripsType = Trip.OFFER;
					}
					else
					{
						tripsType = Trip.REQUEST;
					}
					Intent intent = new Intent(activity, MainActivity.class);
					intent.putExtra(MainActivity.nextScreenParam, MainActivity.TRIP_FREQUENCY_SCREEN_ID);
					intent.putExtra("tripType", tripsType);
					activity.startActivity(intent);
				}

			});
			
			ImageButton refresh = (ImageButton) activity
					.findViewById(R.id.refreshTrip);
			refresh.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					refresh();
				}

			});
			
			ImageButton delete = (ImageButton) activity
					.findViewById(R.id.deleteTrip);
			delete.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					delete();
				}

			});
			OrderCoordinator.handleOrder(
					this,
					RequestFactory.createGetUserTripRequest(tripsType));
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void setOnClickAction() {
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam, MainActivity.MY_TRIPS_DETAILS_SCREEN_ID);
				intent.putExtra("trip", trips.get(position));
				activity.startActivity(intent);

			}

		});
	}
	private void refresh() {
		OrderCoordinator.handleOrder(
				this,
				RequestFactory.createGetUserTripRequest(tripsType));
	}
	private void delete() {
		if(adapter.getChecked().size() > 0)
		{
		OrderCoordinator.handleOrder(
				this,
				RequestFactory.createDeleteTripRequest(getSelectedTrips()));
		}
	}
	public void preExecution() {
		// TODO Auto-generated method stub
		showProgressBar();

	}

	@SuppressWarnings("unchecked")
	public void postExecution(Response response) {
		this.response = response;
		try
		{
			
			if (response.getRequestName().equals(Request.GET_USER_TRIPS_REQUEST)) {
				
				 this.trips = (List<Trip>) ResponseProcessingHelper
				 .getInstance().handleResponse(RequestName.GET_USER_TRIPS_REQUEST,
				 (String)response.getResponse());
				 
				 for(Trip trip : trips)
				   {
					   if(tripsType.equals(Trip.OFFER))
					   {
						   Pool startPool = User.getInstance().getRequestsPools().get(String.valueOf(trip.getFromId()));
						   Pool endPool = User.getInstance().getRequestsPools().get(String.valueOf(trip.getToId()));
						   trip.setStartPool(startPool);
						   trip.setEndPool(endPool);
						  
					   }
					   else if(tripsType.equals(Trip.REQUEST))
					   {
						   Pool startPool = User.getInstance().getOffersPools().get(String.valueOf(trip.getFromId()));
						   Pool endPool = User.getInstance().getOffersPools().get(String.valueOf(trip.getToId()));
						   trip.setStartPool(startPool);
						   trip.setEndPool(endPool);
					   }
				   }
					   
				adapter = new MyTripListImagesAdapter<Trip>(options
						, activity, R.layout.my_trips_list_row, this.trips);
				listView1.setAdapter(adapter);
				listView1.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));
				setOnClickAction();
				listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				closeProgress();
			}
			else if (response.getRequestName().equals(Request.DELETE_TRIP_REQUEST)) {
			{
				closeProgress();
				boolean success = (Boolean) ResponseProcessingHelper.getInstance()
						.handleResponse(Request.DELETE_TRIP_REQUEST,
								(String) response.getResponse());
				if (success) {
					for (int i = 0; i < adapter.getChecked().size(); i++)
					{
						int checked = adapter.getChecked().get(i);
						trips.remove(checked);
						adapter.notifyDataSetChanged();

					}
					adapter = new MyTripListImagesAdapter<Trip>(options
							, activity, R.layout.my_trips_list_row, this.trips);
					listView1.setAdapter(adapter);
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.yourTripsDeletedSuccessfully), Toast.LENGTH_SHORT)
							.show();
				}
				else {
					closeProgress();
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.connectionError), Toast.LENGTH_SHORT)
							.show();
				}
			}
				
			}
			
		
		}
		catch(Exception ex)
		{
			closeProgress();
			Toast.makeText(
					activity.getApplicationContext(),
					activity.getResources().getString(
							R.string.connectionError), Toast.LENGTH_SHORT)
					.show();
		}

	}

	
	private String getSelectedTrips()
	{
//		int len = listView1.getCount();
		String checkedStr = "";
//		SparseBooleanArray checked = listView1.getCheckedItemPositions();
		for (int i = 0; i < adapter.getChecked().size(); i++)
		{
			int checked = adapter.getChecked().get(i);
			checkedStr += ((Trip)trips.get(checked)).getId() + ",";
//			if(checked.get(i) == true) 
//            {
//				checkedStr += ((Trip)trips.get(i)).getId() + ",";
//            }
		}
		return checkedStr;
	}
}
