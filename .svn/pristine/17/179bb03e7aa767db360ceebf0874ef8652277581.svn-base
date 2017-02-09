package com.kartag.gui.presentation;


import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.IActivity;
import com.kartag.gui.R;

public class FeedbackScreen extends AbstractScreen implements IActivity{

	private FragmentActivity activity;
	private EditText text;
	private String[] feedbackTypes = {"Report bug", "Suggest feature", "General inquiry"};
	private int selectedType = -1;
	
	public FeedbackScreen(FragmentActivity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.feedback_layout);
		
		text = (EditText) activity.findViewById(
				R.id.feedbackTxt);
		
		Button send = (Button) activity.findViewById(
				R.id.sendFeedback);
		
		send.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				if(text.getText().toString() == null || "".equals(text.getText().toString()) )
				{
		        	Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.emptyMessage), Toast.LENGTH_SHORT).show();
				}
				else if(selectedType == -1)
				{
		        	Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.selectFeedbackTypeFirst), Toast.LENGTH_SHORT).show();
				}
				else
				{
					sendFeedback("", text.getText().toString());
				}
			}

		});
		final Button selectType = (Button) activity.findViewById(
				R.id.feedbackType);
		
		selectType.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				final FeedbackTypesDialog types = new FeedbackTypesDialog(activity, feedbackTypes);
				types.show();
				
				Button dialogButton = (Button) types.findViewById(R.id.feedbackWheelDismissBtn);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						selectedType = types.getSelectedPool();
						selectType.setText(feedbackTypes[selectedType]);
						types.dismiss();
					}
				});
			}

		});
		ImageButton back = (ImageButton) activity.findViewById(
				R.id.feedbackBackBtn);
		back.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				activity.onBackPressed();
			}

		});
	}
	
	@Override
	public void preExecution() {
		showProgressBar();
	}
	
	@Override
	public void postExecution(Response response) {
		try
		{
		   boolean success = (Boolean) ResponseProcessingHelper.getInstance().handleResponse(Request.ADD_FEEDBACK_REQUEST, (String)response.getResponse());
		   if(success)
		   {
			    closeProgress();
			    text.setText("");
	        	Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.feedbackSentSuccessed), Toast.LENGTH_LONG).show();
		   }
		   else
		   {
	        	Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.connectionError), Toast.LENGTH_LONG).show();
		   }
		}
		catch(Exception ex)
		{
			Log.e("error", Log.getStackTraceString(ex));
		}
	}
	private void sendFeedback(String type,String text)
	{
		OrderCoordinator.handleOrder(this,
 				RequestFactory.createAddFeedbackRequest(type, text));
	}

}
