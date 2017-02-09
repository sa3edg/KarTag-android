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
import com.kartag.persistence.model.bo.Pool;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class SearchTripsFm extends Fragment implements IActivity{
	
	private AbstractScreen parentfm;
	private String[] poolsArray;
	private int startPoolIndex = -1;
	private int endPoolIndex = -1 ;
	private String timeStr = "";
	private String dateStr = "";
	private String tripType = "";
	private CustomProgressDialog progress;
	private ArrayList<Pool> pools;
	public SearchTripsFm(AbstractScreen main, String tripType)
	{
		this.parentfm = main;
		this.tripType = tripType;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.search_layout, container, false);

	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		
		super.onActivityCreated(savedState);
		 if(tripType.equals(Trip.REQUEST))
         {
           pools = new ArrayList<Pool>(User.getInstance().getRequestsPools().values());
         }
         else
         {
         	pools = new ArrayList<Pool>(User.getInstance().getOffersPools().values());
         }
        String[] tempPools = new String[pools.size()];
		int i = 0;
		for(Pool pool : pools)
		{
			tempPools[i] = pool.getPoolName();
			i++;
		}
		poolsArray = tempPools;
       final Button selectStartPool = (Button) parentfm.getActivity().findViewById(R.id.fromBtn);
		selectStartPool.setOnClickListener(new OnClickListener() {
      	 
			public void onClick(View arg0) {
				final PoolsDialog pools = new PoolsDialog(parentfm.getActivity(), poolsArray);
				pools.show();
				
				Button dialogButton = (Button) pools.findViewById(R.id.poolsWheelDismissBtn);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						startPoolIndex = pools.getSelectedPool();
						selectStartPool.setText(poolsArray[startPoolIndex]);
						pools.dismiss();
					}
				});
			}

		});
		
		final Button selectEndPool = (Button) parentfm.getActivity().findViewById(R.id.toBtn);
		selectEndPool.setOnClickListener(new OnClickListener() {
      	 
			public void onClick(View arg0) {
				final PoolsDialog pools = new PoolsDialog(parentfm.getActivity(), poolsArray);
				pools.show();
				Button dialogButton = (Button) pools.findViewById(R.id.poolsWheelDismissBtn);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						endPoolIndex = pools.getSelectedPool();
						selectEndPool.setText(poolsArray[endPoolIndex]);
						pools.dismiss();
					}
				});
			}

		});
		
		final Button pickDate = (Button) parentfm.getActivity().findViewById(R.id.dateBtn);
		pickDate.setOnClickListener(new OnClickListener() {
      	 
			public void onClick(View arg0) {

					final DateTimePickerDialog date = new DateTimePickerDialog(parentfm.getActivity());
					date.show();
					Button dialogButton = (Button) date.findViewById(R.id.okDimissBtn);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dateStr = date.getDate();
							timeStr = date.getTime();
							pickDate.setText(dateStr + "  " + timeStr);
							date.dismiss();
						}
					});
					
				}

		});
		final ImageButton search = (ImageButton) parentfm.getActivity().findViewById(R.id.searchTripsBtn);
		search.setOnClickListener(new OnClickListener() {
      	 
			public void onClick(View arg0) {
				if(dateStr.equals("") && timeStr.equals("") && startPoolIndex == -1 && endPoolIndex == -1)
				{
		        	Toast.makeText(parentfm.getActivity().getApplicationContext(), parentfm.getActivity().getResources().getString(R.string.missingTripDate), Toast.LENGTH_LONG).show();
				}
				else if( startPoolIndex == endPoolIndex )
				{
		        	Toast.makeText(parentfm.getActivity().getApplicationContext(), parentfm.getActivity().getResources().getString(R.string.startPoolEqualEndPool), Toast.LENGTH_LONG).show();
				}
				else
				{
					String startPool = (pools != null && pools.size() > 0  && startPoolIndex != -1) ? ((Pool) pools.get(startPoolIndex)).getId() : "";
					String endPool = (pools != null && pools.size() > 0  && endPoolIndex != -1) ? ((Pool) pools.get(endPoolIndex)).getId() : "";
					String date = ("".equals(dateStr) && "".equals(timeStr)) ? dateStr + " " + timeStr+":00" : "";
				    searchRequests(startPool, endPool, date);
				}
			}

		});
	}
	private void searchRequests(String startpool, String endPool, String date)
	{
		OrderCoordinator.handleOrder(this,
				RequestFactory.createFilterTripsRequest(tripType, startpool, endPool, date));
	}
	@Override
    public void onStart() {
            super.onStart();
    }

	@Override
	public void preExecution() {
		showProgressBar();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void postExecution(Response response) {
		// TODO Auto-generated method stub
		try
		{
		   List<Trip> trips = (List<Trip>) ResponseProcessingHelper.getInstance().handleResponse(Request.GET_FILTERED_TRIPS_REQUEST, (String)response.getResponse());
		   if(trips.size() > 0)
		   {
			   closeProgress();
			   for(Trip trip : trips)
			   {
				   if(tripType.equals(Trip.OFFER))
				   {
					   Pool startPool = User.getInstance().getOffersPools().get(String.valueOf(trip.getFromId()));
					   Pool endPool = User.getInstance().getOffersPools().get(String.valueOf(trip.getToId()));
					   trip.setStartPool(startPool);
					   trip.setEndPool(endPool);
				   }
				   else if(tripType.equals(Trip.REQUEST))
				   {
					   Pool startPool = User.getInstance().getRequestsPools().get(String.valueOf(trip.getFromId()));
					   Pool endPool = User.getInstance().getRequestsPools().get(String.valueOf(trip.getToId()));
					   trip.setStartPool(startPool);
					   trip.setEndPool(endPool);
				   }
				   
			   }
		   Pool dumyPool = new Pool();
		   dumyPool.setTrips(trips);
		   Intent intent = new Intent(parentfm.getActivity(), MainActivity.class);
			intent.putExtra(MainActivity.nextScreenParam, MainActivity.TRIP_LIST_ID);
			intent.putExtra("pool", dumyPool);
			parentfm.getActivity().startActivity(intent);
		   }
		   else
		   {
			   closeProgress();
	        	Toast.makeText(parentfm.getActivity().getApplicationContext(), parentfm.getActivity().getResources().getString(R.string.noFilteredTrips), Toast.LENGTH_LONG).show();
		   }

		}
		catch(Exception ex)
		{
			Log.e("error", Log.getStackTraceString(ex));
			closeProgress();
			Toast.makeText(
					parentfm.getActivity().getApplicationContext(),
					parentfm.getActivity().getResources().getString(
							R.string.connectionError), Toast.LENGTH_SHORT)
					.show();
		}
	}
public void showProgressBar() {
		progress = new CustomProgressDialog(parentfm.getActivity());
		progress.show();
	}

	
	public void closeProgress() {
		progress.dismiss();
	}
	private class CustomProgressDialog extends Dialog {

		private Context context;

		public CustomProgressDialog(Context context) {
			super(context);
			this.context = context;
			loadScreen();
		}


		/**
		 * Initializes wheel
		 * 
		 * @param id
		 *            the wheel widget Id
		 */
		public void loadScreen() {
			// TODO Auto-generated method stub
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.progress);
	        setCancelable(false);
	        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

		}

	}

}
