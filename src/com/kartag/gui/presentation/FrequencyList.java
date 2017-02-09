package com.kartag.gui.presentation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;

public class FrequencyList extends AbstractScreen{

	private String tripType = "";
	public FrequencyList(FragmentActivity activity, String tripType) {
		super(activity);
		this.tripType = tripType;
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.trip_frequency_layout);
		
		ImageButton back = (ImageButton) activity.findViewById(
				R.id.backBtn);
		back.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				activity.onBackPressed();
			}

		});
		ImageButton weekly = (ImageButton) activity.findViewById(
				R.id.weeklyBtn);
		weekly.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam, MainActivity.ADD_TRIP_SCREEN_ID);
			    intent.putExtra("frequency", "weekly");
				intent.putExtra("tripType", tripType);
				activity.startActivity(intent);
			}

		});
		
		ImageButton once = (ImageButton) activity.findViewById(
				R.id.onceBtn);
		once.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam, MainActivity.ADD_TRIP_SCREEN_ID);
				intent.putExtra("frequency", "once");
				intent.putExtra("tripType", tripType);
				activity.startActivity(intent);
			}

		});
		
	}
}
