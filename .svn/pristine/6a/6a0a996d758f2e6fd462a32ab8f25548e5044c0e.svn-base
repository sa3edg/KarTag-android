package com.kartag.gui.presentation;

import java.util.Arrays;
import java.util.List;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.IActivity;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.persistence.dao.PersistenceSessionFactory;
import com.kartag.persistence.dao.bd.SettingDao;
import com.kartag.persistence.model.bo.Setting;
import com.kartag.persistence.model.bo.User;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class LoginActivity extends Activity implements IActivity{

	private static final String TAG = LoginActivity.class.getSimpleName();;

	private UiLifecycleHelper uiHelper;

	private static final List<String> publishPermissions = Arrays
			.asList("read_stream", "publish_actions" , "user_events");
	
	private static final List<String> readPermissions = Arrays
			.asList("email", "user_events");

	private CustomProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(readPermissions);
		//authButton.setPublishPermissions(publishPermissions);
		
	}

	@Override
	public void onResume() {
		super.onResume();

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			showProgressBar();
			OrderCoordinator.handleOrder(this,
					RequestFactory.createFacebookLoginRequest(this));
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	public void showProgressBar() {

		progress = new CustomProgressDialog(this);
		progress.show();
	}

	public void closeProgress() {
		if (progress != null) {
			progress.dismiss();
		}
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
			getWindow().getDecorView().setBackgroundResource(
					android.R.color.transparent);

		}

	}

	@Override
	public void preExecution() {
		
		// TODO Auto-generated method stub
//		OpenRequest open = new OpenRequest(this);
//        open.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
//        open.setPermissions(Arrays.asList(new String[]{"email", "publish_actions", "user_birthday", "user_hometown"}));
//        open.setCallback(this);
//        Session s = new Session(this);
//        s.openForPublish(open);
	}

	@Override
	public void postExecution(Response response) {
		// TODO Auto-generated method stub
		try
		{
		if (response.getRequestName().equals(Request.FB_LOGIN_ORDER)) {
			OrderCoordinator.handleOrder(this,
					RequestFactory.createFacebookGetFriendsRequest(this));
		} else if (response.getRequestName().equals(
				Request.FB_GET_FRIENDS_ORDER)) {
//			OrderCoordinator.handleOrder(this, RequestFactory
//					.createLoginRequest(User.getInstance().getProfile()));
		} else if (response.getRequestName().equals(Request.ADD_USER_REQUEST)) {
			try {
				boolean added = (Boolean) ResponseProcessingHelper
						.getInstance().handleResponse(Request.ADD_USER_REQUEST,
								(String)response.getResponse());
				if (added) {
					showHomePage();
				}else
				{
					closeProgress();
					Toast.makeText(
							this.getApplicationContext(),
							this.getResources().getString(
									R.string.connectionError), Toast.LENGTH_SHORT)
							.show();
				}
				
			} catch (Exception ex) {
				
				closeProgress();
				Toast.makeText(
						this.getApplicationContext(),
						this.getResources().getString(
								R.string.connectionError), Toast.LENGTH_SHORT)
						.show();
			}
		}
		}
		catch(Exception ex)
		{

				
				closeProgress();
				Toast.makeText(
						this.getApplicationContext(),
						this.getResources().getString(
								R.string.connectionError), Toast.LENGTH_SHORT)
						.show();
		}
	}
	
	private void showHomePage()
	{
		closeProgress();
		SettingDao dao = new SettingDao(PersistenceSessionFactory.createSessionInstance(MainActivity.getActivity()).currentSession());
		Setting setting = (Setting)dao.get(new Setting());
		if(setting != null)
		{
			User.getInstance().setSetting(setting);
		}
		else
		{
			dao.store(new Setting(), null);
			User.getInstance().setSetting(new Setting());
		}
		Intent intent = new Intent(MainActivity.getActivity(), MainActivity.class);
		intent.putExtra(MainActivity.nextScreenParam,
				MainActivity.HOME_ID);
		MainActivity.getActivity().startActivity(intent);
	}
}
