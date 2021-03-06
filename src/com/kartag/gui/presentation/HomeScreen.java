package com.kartag.gui.presentation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.notification.NotificationHandler;
import com.kartag.persistence.model.bo.Trip;

public class HomeScreen extends AbstractScreen{

	public HomeScreen(FragmentActivity activity) {
		super(activity);
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.home_layout);
		NotificationHandler.getInstance(activity).startNotificationHandler();
		ImageButton haveAride = (ImageButton) activity.findViewById(R.id.btnHaveAride);
		haveAride.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				
				
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam, MainActivity.HAVE_ARIDE_SCREEN_ID);
				activity.startActivity(intent);
			}
 
		});
		
		ImageButton needAride = (ImageButton) activity.findViewById(R.id.btnNeedAride);
		needAride.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MainActivity.class);
				intent.putExtra(MainActivity.nextScreenParam, MainActivity.NEED_ARIDE_SCREEN_ID);
				activity.startActivity(intent);
				
			}
 
		});
	}

}
