package com.kartag.gui.presentation;

import com.kartag.gui.R;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;

public class SeatsDialog extends Dialog {

	private Context context;
	private String[] pools;
	private WheelView seatssWheel;

	public SeatsDialog(Context context, String[] pools) {
		super(context);
		this.context = context;
		this.pools = pools;
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
        setContentView(R.layout.seats_dialog_layout);
        seatssWheel = (WheelView) findViewById(R.id.seatsWheel);
        
        ArrayWheelAdapter<String> ampmAdapter =
            new ArrayWheelAdapter<String>(this.getContext(), pools);
        ampmAdapter.setItemResource(R.layout.wheel_text_item);
        ampmAdapter.setItemTextResource(R.id.text);
        seatssWheel.setViewAdapter(ampmAdapter);
	}
	public int getSelectedSeat()
	{
		return seatssWheel.getCurrentItem();
	}

}
