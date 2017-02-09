package com.kartag.gui.presentation;

import java.util.List;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage.MessageLevel;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.DummyTabContent;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.actionbar.ActionBar;
import com.kartag.gui.actionbar.ActionBar.AbstractAction;
import com.kartag.gui.adapter.MessagesListAdapter;
import com.kartag.performance.ImagesMemoryHandler;
import com.kartag.persistence.model.bo.Message;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class InboxScreen extends AbstractScreen {

	public ActionBar actionBar;
	private TabHost tHost;
	private MessagesList messagesList = null;
	private NotificationsList notificationsList = null;
	private DisplayImageOptions options;
	public InboxScreen(FragmentActivity activity) {
		super(activity);
	}

	@Override
	public void loadScreen() {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.inbox_layout);
		options = ImagesMemoryHandler.getDisplayImageOptions();

//		actionBar = (ActionBar) activity.findViewById(R.id.myTripsAction);
//		actionBar.addAction(new TripsAction());
//        actionBar.addAction(new InboxAction());
//        actionBar.addAction(new FeedbackAction());
//        actionBar.addAction(new SettingAction());

		final String messagesTabTitle = activity.getResources().getString(
				R.string.messagesTabTitle);
		final String notificationTabTitle = activity.getResources().getString(
				R.string.notificationTabTitle);
	
		tHost = (TabHost) activity.findViewById(android.R.id.tabhost);
		tHost.setup();

		final ImageButton delete = (ImageButton) activity.findViewById(R.id.deleteInbox);
		delete.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				delete();
			}
 
		});
		/**
		 * Defining Tab Change Listener event. This is invoked when tab is
		 * changed
		 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				
				android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
				MessagesList messagesList = (MessagesList) fm
						.findFragmentByTag(messagesTabTitle);
				
				NotificationsList notificationList = (NotificationsList) fm
						.findFragmentByTag(notificationTabTitle);
				
				android.support.v4.app.FragmentTransaction ft = fm
						.beginTransaction();

				/** Detaches the androidfragment if exists */
				if (messagesList != null)
					ft.detach(messagesList);

				/** Detaches the applefragment if exists */
				if (notificationList != null)
					ft.detach(notificationList);
				

				/** If current tab is android */
//				tHost.getCurrentTab()
				if (tabId.equalsIgnoreCase(messagesTabTitle)) {
					delete.setVisibility(View.VISIBLE);
//				if(tHost.getCurrentTab() == 0) {
					
					if (messagesList == null) {
						
						if (getMessagesList() == null) {
							createMessagesList();
						}
						ft.add(R.id.inboxRealtabcontent, getMessagesList(),
								messagesTabTitle);

					} else {
						
						ft.attach(messagesList);
					}

				}
				else if (tabId.equalsIgnoreCase(notificationTabTitle)) {
					delete.setVisibility(View.GONE);
						if (notificationList == null) {
							
							if (getNotificationsList() == null) {
								createNotificationsList();
							}
							ft.add(R.id.inboxRealtabcontent, getNotificationsList(),
									notificationTabTitle);

						} else {
							
							ft.attach(notificationList);
						}

					}
				
				ft.commit();
			}

		};

		tHost.setOnTabChangedListener(tabChangeListener);

		View indicator1 = activity.getLayoutInflater().inflate(R.layout.inbox_indicator_layout, null);
		TextView title1 = (TextView)indicator1.findViewById(R.id.messagesIndicatorTitle);
		title1.setText(messagesTabTitle);
		

		TabHost.TabSpec tSpecNotificationTab = tHost.newTabSpec(notificationTabTitle);
//		tSpecNotificationTab.setIndicator(notificationTabTitle, activity.getResources()
//				.getDrawable(R.drawable.friends_req));
		tSpecNotificationTab.setIndicator(notificationTabTitle);
		tSpecNotificationTab.setContent(new DummyTabContent(activity.getBaseContext()));
		tHost.addTab(tSpecNotificationTab);
		
		TabHost.TabSpec tSpecMessagesTab = tHost.newTabSpec(messagesTabTitle);
//		tSpecMessagesTab.setIndicator(messagesTabTitle, activity.getResources()
//				.getDrawable(R.drawable.friends_req));
//		tSpecMessagesTab.setIndicator(indicator1);
		tSpecMessagesTab.setIndicator(messagesTabTitle);
		tSpecMessagesTab.setContent(new DummyTabContent(activity.getBaseContext()));
		tHost.addTab(tSpecMessagesTab);
		
		ImageButton back = (ImageButton) activity.findViewById(R.id.inboxBackBtn);
		back.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				activity.onBackPressed();
			}
 
		});
		
		ImageButton refresh = (ImageButton) activity.findViewById(R.id.refreshInbox);
		refresh.setOnClickListener(new OnClickListener() {
        	 
			public void onClick(View arg0) {
				refresh();
			}
 
		});
		
		
 
	}

		@Override
		public void preExecution() {
			showProgressBar();
			
		}

		@Override
		@SuppressWarnings("unchecked")
		public void postExecution(Response response) {
			try {
				if (response.getRequestName().equals(Request.GET_USER_MESSAGES_REQUEST)) {
					closeProgress();
					List<Message> messages = (List<Message>) ResponseProcessingHelper
							.getInstance().handleResponse(
									Request.GET_USER_MESSAGES_REQUEST,
									(String) response.getResponse());
					MessagesListAdapter<Message> adapter = new MessagesListAdapter<Message>(
							options, activity,
							R.layout.messages_list_row, messages);

					/** Setting the array adapter to the listview */

					messagesList.setListAdapter(adapter);
					messagesList.getListView().setOnScrollListener(
							new PauseOnScrollListener(ImageLoader.getInstance(),
									false, true));
				}
				else if (response.getRequestName().equals(Request.DELETE_MESSAGE_REQUEST)) {
					{
						closeProgress();
						boolean success = (Boolean) ResponseProcessingHelper.getInstance()
								.handleResponse(Request.DELETE_MESSAGE_REQUEST,
										(String) response.getResponse());
						if (success) {
							for (int i = 0; i < messagesList.getAdapter().getChecked().size(); i++)
							{
								int checked = messagesList.getAdapter().getChecked().get(i);
								messagesList.getMessages().remove(checked);
								messagesList.getAdapter().notifyDataSetChanged();

							}
							messagesList.setAdapter(new MessagesListAdapter<Message>(
									options, activity,
									R.layout.messages_list_row, messagesList.getMessages()));
							
							Toast.makeText(
									activity.getApplicationContext(),
									activity.getResources().getString(
											R.string.yourMessagesDeletedSuccessfully), Toast.LENGTH_SHORT)
									.show();
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
			} catch (Exception ex) {
				Log.e("error", Log.getStackTraceString(ex));
			}
		}

		
		private void createMessagesList() {
			setMessagesList(new MessagesList(this));
		}
		private void createNotificationsList() {
			setNotificationsList(new NotificationsList(this));
		}
		
		
		public FragmentActivity getActivity()
		{
			return activity;
		}

		public MessagesList getMessagesList() {
			return messagesList;
		}

		public void setMessagesList(MessagesList messagesList) {
			this.messagesList = messagesList;
		}

		public NotificationsList getNotificationsList() {
			return notificationsList;
		}

		public void setNotificationsList(
				NotificationsList notificationsList) {
			this.notificationsList = notificationsList;
		}
		private void refresh() {
			OrderCoordinator.handleOrder(
					this,
					RequestFactory.createGetUserMessagesRequest(User.getInstance()
							.getProfile().getId()));
		}
		
		private void delete() {
			if(messagesList.getAdapter().getChecked().size() > 0)
			{
			OrderCoordinator.handleOrder(
					this,
					RequestFactory.createDeleteMessageRequest(getSelectedMessages()));
			}
		}
		
		private String getSelectedMessages()
		{
			String checkedStr = "";
			for (int i = 0; i < messagesList.getAdapter().getChecked().size(); i++)
			{
				int checked =  messagesList.getAdapter().getChecked().get(i);
				checkedStr += ((Message)messagesList.getMessages().get(checked)).getId() + ",";
			}
			return checkedStr;
		}

}
