package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.IActivity;
import com.kartag.gui.R;
import com.kartag.gui.adapter.NotificationListAdapter;
import com.kartag.performance.ImagesMemoryHandler;
import com.kartag.persistence.model.bo.Notification;
import com.kartag.persistence.model.bo.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class NotificationsList extends ListFragment implements IActivity{
	
	private List<Notification> notifications;
	private int selectedIndex = -1;
	private InboxScreen parentfm;
	private DisplayImageOptions options;
	public NotificationsList(InboxScreen main)
	{
		this.parentfm = main;
		options = ImagesMemoryHandler.getDisplayImageOptions();
		OrderCoordinator.handleOrder(this,
 				RequestFactory.createGetUserNotificationsRequest(User.getInstance().getProfile().getId()));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
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

	    getListView().setOnItemClickListener(new ListView.OnItemClickListener() {
	        //@Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
	                // show map here
	        	    setSelectedIndex(i);
	                //main.showMap();
	            }
	    });
	            
	    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

	        //@Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	        	//edit event here
	        	
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
		try
		{
		   this.notifications = (List<Notification>) ResponseProcessingHelper.getInstance().handleResponse(Request.GET_USER_NOTIFICATION_REQUEST, (String)response.getResponse());
		   NotificationListAdapter<Notification> adapter = new NotificationListAdapter<Notification>(options, parentfm.getActivity(), 
				   R.layout.notifications_list_row, notifications);
		   
		/** Setting the array adapter to the listview */
		   User.getInstance().setNotifications(0);
		   setListAdapter(adapter);
		   getListView().setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));

		}
		catch(Exception ex)
		{
			 this.notifications = new ArrayList<Notification>();
			   NotificationListAdapter<Notification> adapter = new NotificationListAdapter<Notification>(options, parentfm.getActivity(), 
					   R.layout.notifications_list_row, notifications);
			   
			/** Setting the array adapter to the listview */
			   
			   setListAdapter(adapter);
			Toast.makeText(
					parentfm.getActivity().getApplicationContext(),
					parentfm.getActivity().getResources().getString(
							R.string.connectionError), Toast.LENGTH_SHORT)
					.show();
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
