package com.kartag.gui.presentation;

import com.kartag.gui.R;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class PoolsDialog extends Dialog {

	private Context context;
	private String[] pools;
	private WheelView poolsWheel;

	public PoolsDialog(Context context, String[] pools) {
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
        setContentView(R.layout.pools_dialog_layout);
        getWindow().setLayout (LayoutParams.MATCH_PARENT /* width */ , LayoutParams.WRAP_CONTENT /* height */);
        poolsWheel = (WheelView) findViewById(R.id.poolsWheel);
        //poolsWheel.setLayoutParams(getWindow().getAttributes());
        
        ArrayWheelAdapter<String> ampmAdapter =
            new ArrayWheelAdapter<String>(this.getContext(), pools);
        ampmAdapter.setItemResource(R.layout.wheel_text_item);
        ampmAdapter.setItemTextResource(R.id.text);
        poolsWheel.setViewAdapter(ampmAdapter);
        
	}
	public int getSelectedPool()
	{
		return poolsWheel.getCurrentItem();
	}

}
