package com.kartag.gui.presentation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;
import com.kartag.util.DateUtils;
import com.kartag.util.UserUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class TripDetailsScreen extends AbstractScreen {

	private Trip trip;
	private RatingBar rateBar;
	private boolean isMyTrip = false;

	public TripDetailsScreen(FragmentActivity activity, Trip trip) {
		super(activity);
		this.trip = trip;
	}
	public TripDetailsScreen(FragmentActivity activity, Trip trip, boolean isMyTrip) {
		super(activity);
		this.trip = trip;
		this.isMyTrip = isMyTrip;
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.trip_details_layout);
		createTabs();
	}

	private void createTabs() {

		ImageView thumb_image = (ImageView) activity
				.findViewById(R.id.profile_pic); // thumb image

		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(100))
		.build();;
		ImageLoader.getInstance().displayImage(
				UserUtils.getProfilePictureURL(trip.getUid()), thumb_image, options);

		TextView name = (TextView) activity.findViewById(R.id.userName);
		name.setText(trip.getUser().getName());

//		String mutualFriends = "0";
//		if(trip.getUser().getMutual_friend_count().equals("0"))
//		{
//		   if(User.getInstance().getProfile().getFriends().size() > 0 && User.getInstance().getProfile().getFriends().containsKey(trip.getUid()))
//		   {
//			   mutualFriends = User.getInstance().getProfile().getFriends().get(trip.getUid()).getMutual_friend_count();
//		   }
//		   else if(User.getInstance().getProfile().getFriendsOfFriends().size() > 0&& User.getInstance().getProfile().getFriendsOfFriends().containsKey(trip.getUid()))
//		   {
//			   mutualFriends = User.getInstance().getProfile().getFriendsOfFriends().get(trip.getUid()).getMutual_friend_count();
//		   }
//		}
//		else
//		{
//			mutualFriends = trip.getUser().getMutual_friend_count();
//		}
//		TextView mutual = (TextView) activity.findViewById(R.id.mutualFriends);
//		mutual.setText("( " + mutualFriends + " mutual friends)");

		rateBar = (RatingBar) activity.findViewById(R.id.ratingBar);
		if(User.getInstance().getUpdatedTrips().containsKey(trip.getId()))
		{
			rateBar.setRating(User.getInstance().getUpdatedTrips().get(trip.getId()).getRate());
		}
		else
		{
		   rateBar.setRating(trip.getRate());
		}

//		rateBar.setOnTouchListener(new OnTouchListener() {
//			@Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                	sendRate();
//                	v.setPressed(true);
//                }
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    v.setPressed(true);
//                }
//
//                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    v.setPressed(false);
//                }
//                return true;
//            }
//
//			
//			});
		rateBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

	        @Override
	        public void onRatingChanged(RatingBar ratingBar, float rating,
	                boolean fromUser) {

	        	sendRate();
	        }
	    });
		
		TabHost tabs = (TabHost) activity.findViewById(R.id.TabHost01);

		tabs.setup();

		TabHost.TabSpec spec1 = tabs.newTabSpec("tag1");

		spec1.setContent(R.id.tripDetailsTab);
		spec1.setIndicator(activity.getResources().getString(
				R.string.detailsTab));

		TabHost.TabSpec spec2 = tabs.newTabSpec("tag2");
		spec2.setContent(R.id.commentTab);
		// spec2.setc
		spec2.setIndicator(activity.getResources().getString(
				R.string.commentTab));

		

		tabs.addTab(spec1);
		tabs.addTab(spec2);
		TextView  from= (TextView) activity.findViewById(R.id.fromText);
		from.setText(trip.getStartPool().getPoolName() + " To " + trip.getEndPool().getPoolName());
		
//		TextView to = (TextView) activity.findViewById(R.id.toText);
//		to.setText(trip.getEndPool().getPoolName());
		
		TextView date = (TextView) activity.findViewById(R.id.dateText);
		String[] time = DateUtils.getTripTimeFromMilliseconds(trip.getTime());
		date.setText(time[0] + " " + time[1]);
		
		TextView comment = (TextView) activity.findViewById(R.id.comentText);
		comment.setText(trip.getComment());
		
		CheckBox isSmoking = (CheckBox) activity.findViewById(R.id.smokingchekcbox);
		if(trip.isSmokingAllowed())
		{
			isSmoking.setVisibility(View.VISIBLE);
		}
	
		CheckBox isFriendsOnly = (CheckBox) activity.findViewById(R.id.friendshekcbox);
		if(trip.isFriendsOnly())
		{
			isFriendsOnly.setVisibility(View.VISIBLE);
		}
		
		CheckBox isWomenOnly = (CheckBox) activity.findViewById(R.id.womenchekcbox);
		if(trip.isWomenOnly())
		{
			isWomenOnly.setVisibility(View.VISIBLE);
		}
		
		// handle send request button
		Button sendRequest = (Button) activity
				.findViewById(R.id.join);
		sendRequest.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if(User.getInstance().getProfile().getId().equals(trip.getUid()))
				{
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.youCantJoin), Toast.LENGTH_LONG)
							.show();
				}
				else
				{
				   joinTrip();
				}
			}

		});

		// handle send request button
		Button sendMessage = (Button) activity
				.findViewById(R.id.message);
		sendMessage.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				sendMessage();
			}

		});

		ImageButton back = (ImageButton) activity.findViewById(R.id.addTripBackBtn);
		back.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				activity.onBackPressed();
			}
 
		});
		if(isMyTrip)
		{
			sendRequest.setVisibility(View.GONE);
		}
		
		for (int tabIndex = 0 ; tabIndex < tabs.getTabWidget().getTabCount() ; tabIndex ++) {
		    View tab = tabs.getTabWidget().getChildTabViewAt(tabIndex);
		    TextView t = (TextView)tab.findViewById(android.R.id.title);
		    t.setTextColor(activity.getResources().getColor(R.color.white));
		}

	}

	private void joinTrip() {
		
		OrderCoordinator.handleOrder(
				this,
				RequestFactory.createJoinTripRequest(trip.getId(),
						trip.getType()));
	}

	private void sendMessage() {
		Intent intent = new Intent(activity, MainActivity.class);
		intent.putExtra(MainActivity.nextScreenParam,
				MainActivity.SENT_MESSAGE_ID);
		intent.putExtra("tripOwner", trip.getUser());
		activity.startActivity(intent);
	}

	private void sendRate() {
		OrderCoordinator.handleOrder(
				this,
				RequestFactory.createRateTripRequest(trip.getId(),
						String.valueOf(rateBar.getRating())));
	}

	@Override
	public void postExecution(Response response) {
		closeProgress();
		try
		{
		if (response.getRequestName().equals(Request.SEND_JOIN_TRIP_REQUEST)) {

			String result = (String) ResponseProcessingHelper.getInstance()
					.handleResponse(Request.SEND_JOIN_TRIP_REQUEST,
							(String) response.getResponse());
			if (Trip.REQUEST_SENT.equals(result)) {
			Toast.makeText(
					activity.getApplicationContext(),
					activity.getResources().getString(
							R.string.yourJoinResuestSent), Toast.LENGTH_LONG)
					.show();
			}
			else if (Trip.REJECTED.equals(result)) {
				Toast.makeText(
						activity.getApplicationContext(),
						activity.getResources().getString(
								R.string.youJoinRequestRejected), Toast.LENGTH_LONG)
						.show();
				}
			else if (Trip.JOIN_REQUEST_ALREADY_EXIST.equals(result)) {
				Toast.makeText(
						activity.getApplicationContext(),
						activity.getResources().getString(
								R.string.youJoinRequestExist), Toast.LENGTH_LONG)
						.show();
				}
			else if (Trip.JOIN_REQUEST_ALREADY_ACCEPTED.equals(result)) {
				Toast.makeText(
						activity.getApplicationContext(),
						activity.getResources().getString(
								R.string.youJoinRequestAccepted), Toast.LENGTH_LONG)
						.show();
				}
			
			else {
				Toast.makeText(
						activity.getApplicationContext(),
						activity.getResources().getString(
								R.string.connectionError), Toast.LENGTH_SHORT)
						.show();
			}
		} else if (response.getRequestName().equals(Request.RATE_TRIP_REQUEST)) {

			boolean success = (Boolean) ResponseProcessingHelper.getInstance()
					.handleResponse(Request.RATE_TRIP_REQUEST,
							(String) response.getResponse());
			if (success) {
				Trip temp = this.trip;
				temp.setRate((int)rateBar.getRating());
				User.getInstance().getUpdatedTrips().put(trip.getId(), temp);
			Toast.makeText(
					activity.getApplicationContext(),
					activity.getResources()
							.getString(R.string.yourJoinRateSent),
					Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(
						activity.getApplicationContext(),
						activity.getResources().getString(
								R.string.connectionError), Toast.LENGTH_SHORT)
						.show();
			}
		}
		}
		catch(Exception ex)
		{
			Toast.makeText(
					activity.getApplicationContext(),
					activity.getResources().getString(
							R.string.connectionError), Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void preExecution() {
		showProgressBar();
	}

}
