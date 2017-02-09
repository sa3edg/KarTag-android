package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.IActivity;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.adapter.MessagesListAdapter;
import com.kartag.gui.adapter.NotificationListAdapter;
import com.kartag.performance.ImagesMemoryHandler;
import com.kartag.persistence.model.bo.Message;
import com.kartag.persistence.model.bo.Notification;
import com.kartag.persistence.model.bo.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
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

public class MessagesList extends ListFragment implements IActivity {

	private List<Message> messages;
	private int selectedIndex = -1;
	private InboxScreen parentfm;
	private DisplayImageOptions options;
	private MessagesListAdapter<Message> adapter;

	public MessagesList(InboxScreen main) {
		this.parentfm = main;
		options = ImagesMemoryHandler.getDisplayImageOptions();
		OrderCoordinator.handleOrder(
				this,
				RequestFactory.createGetUserMessagesRequest(User.getInstance()
						.getProfile().getId()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);

		// View v = inflater.inflate(R.layout.messages_list_view, container,
		// false);
		// listView = (ListView)v.findViewById(R.id.messagesListView);
		// return v;
		// return inflater.inflate(R.layout.messages_list_view, container,
		// false);

	}

	@Override
	public void onStart() {
		super.onStart();
		/** Setting the multiselect choice mode for the listview */
		getListView().setDescendantFocusability(
				ListView.FOCUS_BLOCK_DESCENDANTS);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		// listView = (ListView)
		// parentfm.getActivity().findViewById(R.id.messagesListView);
		getListView().setOnItemClickListener(
				new ListView.OnItemClickListener() {
					// @Override
					public void onItemClick(AdapterView<?> a, View v, int i,
							long l) {
						// show map here
						Log.i("info", "list clicked");

						setSelectedIndex(i);
						Intent intent = new Intent(parentfm.getActivity(),
								MainActivity.class);
						intent.putExtra(MainActivity.nextScreenParam,
								MainActivity.MESSAGE_DETAILS_ID);
						intent.putExtra("message", getMessages().get(i));
						parentfm.getActivity().startActivity(intent);
					}
				});

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			// @Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// edit event here

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
		try {
			if (response.getRequestName().equals(Request.GET_USER_MESSAGES_REQUEST)) {
				this.setMessages((List<Message>) ResponseProcessingHelper
						.getInstance().handleResponse(
								Request.GET_USER_MESSAGES_REQUEST,
								(String) response.getResponse()));
				setAdapter(new MessagesListAdapter<Message>(
						options, parentfm.getActivity(),
						R.layout.messages_list_row, getMessages()));

				/** Setting the array adapter to the listview */

				setListAdapter(getAdapter());
				getListView().setOnScrollListener(
						new PauseOnScrollListener(ImageLoader.getInstance(),
								false, true));
			}
			
		} catch (Exception ex) {
			Log.e("error", Log.getStackTraceString(ex));
			this.messages = new ArrayList<Message>();
			MessagesListAdapter<Message> adapter = new MessagesListAdapter<Message>(
					options, parentfm.getActivity(),
					R.layout.messages_list_row, getMessages());

			setListAdapter(adapter);
			   			   
			   setListAdapter(adapter);
			Toast.makeText(
					parentfm.getActivity().getApplicationContext(),
					parentfm.getActivity().getResources().getString(
							R.string.connectionError), Toast.LENGTH_SHORT)
					.show();
		}
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public MessagesListAdapter<Message> getAdapter() {
		return adapter;
	}

	public void setAdapter(MessagesListAdapter<Message> adapter) {
		this.adapter = adapter;
	}
}
