package com.kartag.gui;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kartag.client.common.Response;

public abstract class AbstractScreen implements IActivity {

	protected Response response;
	protected FragmentActivity activity;
	private CustomProgressDialog progress;

	public AbstractScreen(FragmentActivity activity) {
		this.activity = activity;
	}

	public abstract void loadScreen();

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postExecution(Response response) {
		// TODO Auto-generated method stub

	}

	public void showProgressBar() {
		
//		ProgressDialog dialog = new ProgressDialog(activity);
//	    //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		dialog.setContentView(R.layout.progress);
//	    dialog.setMax(100);
//	    dialog.setCancelable(true);
//	    dialog.show();
//		activity.setContentView(R.layout.progress);
//		ProgressBar pbar = (ProgressBar) activity.findViewById(
//				R.id.progressbar);
//		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		ProgressBar pbar = new ProgressBar(activity, null,
//				android.R.attr.progressBarStyleLargeInverse);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 50);
//		pbar.setLayoutParams(params);
//		pbar.setLayoutParams(new LayoutParams(50, 50));
//		pbar.setIndeterminate(true);
//		
//		builder.setView(pbar);
//		builder.setCancelable(true);
//		
//		progressBar = builder.create();
//		progressBar.setContentView(R.layout.progress);
//		progressBar.show();
		
//		ProgressDialog pd=new ProgressDialog(activity);
//		pd.requestWindowFeature(Window.FEATURE_PROGRESS);
//		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		        pd.setIndeterminate(true);
//		        pd.setCancelable(false);
//		        pd.show();
		
		progress = new CustomProgressDialog(activity);
		progress.show();
	}

	
	public void closeProgress() {
		if(progress != null)
		{
		   progress.dismiss();
		}
	}

	public FragmentActivity getActivity() {
		return activity;
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
