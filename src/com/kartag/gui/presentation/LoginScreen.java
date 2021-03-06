package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.widget.LoginButton;
import com.google.android.gcm.GCMRegistrar;
import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.notification.gcm.GCMIntentService;
import com.kartag.persistence.dao.PersistenceSessionFactory;
import com.kartag.persistence.dao.bd.SettingDao;
import com.kartag.persistence.model.bo.Community;
import com.kartag.persistence.model.bo.Setting;
import com.kartag.persistence.model.bo.User;
import com.kartag.util.UserUtils;

public class LoginScreen extends AbstractScreen {

	private LoginButton login;
	private List<Community> communities;
	private String communityId = "";
	private Dialog dialog;
	private Dialog forgotPasswordDialog;
	private Dialog changePasswordDialog;
	public LoginScreen(FragmentActivity activity) {
		super(activity);
	}

	@Override
	public void loadScreen() {

		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.login_layout);

		login = (LoginButton) activity.findViewById(R.id.loginbtn);
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				login();
			}

		});

	}

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub
		// showProgressBar();
	}

	private void login() {
		// login.setEnabled(false);
		showProgressBar();
		OrderCoordinator.handleOrder(this,
				RequestFactory.createFacebookLoginRequest(activity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postExecution(Response response) {
		try {
			if (response.getRequestName().equals(Request.FB_LOGIN_ORDER)) {
				OrderCoordinator.handleOrder(this, RequestFactory
						.createFacebookGetFriendsRequest(activity));
			} else if (response.getRequestName().equals(
					Request.FB_GET_FRIENDS_ORDER)) {
				if ("".equals(GCMIntentService.REG_ID)) {
					GCMIntentService.REG_ID = GCMRegistrar
							.getRegistrationId(activity);
				}
				// check exist communityId
				SettingDao dao = new SettingDao(PersistenceSessionFactory
						.createSessionInstance(activity).currentSession());
				Setting setting = (Setting) dao.get(new Setting());
				if (setting != null) {
//					Toast.makeText(
//							activity.getApplicationContext(),
//							"community id: " + setting.getCommunityId(),
//							Toast.LENGTH_LONG).show();
					User.getInstance().setSetting(setting);
				} else {
					dao.store(new Setting(), null);
					User.getInstance().setSetting(new Setting());
				}
				if ("".equals(User.getInstance().getSetting().getCommunityId())) {
					OrderCoordinator.handleOrder(this, RequestFactory
							.createGetCommunities(User.getInstance()
									.getCountryId()));
				} else {
					//User.getInstance().setCommunityId(User.getInstance().getSetting().getCommunityId());
					register("", "");
					//showLoginDialog();
				}

			} else if (response.getRequestName()
					.equals(Request.CHANGE_PASSWORD)) {
				
				boolean result = (Boolean) ResponseProcessingHelper
						.getInstance().handleResponse(
								Request.CHANGE_PASSWORD,
							(String) response.getResponse());
				closeProgress();
				if(result){
					changePasswordDialog.dismiss();
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.passwordChangeSucceed),
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.passwordChangeFailed),
							Toast.LENGTH_LONG).show();
				}
				
			}else if (response.getRequestName()
					.equals(Request.FORGET_PASSWORD)) {
				boolean result = (Boolean) ResponseProcessingHelper
						.getInstance().handleResponse(
								Request.FORGET_PASSWORD,
								(String) response.getResponse());
				closeProgress();
				if(result){
					forgotPasswordDialog.dismiss();
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.forgetChangeSucceed),
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.forgetChangeFailed),
							Toast.LENGTH_LONG).show();
				}
				
			}else if (response.getRequestName()
					.equals(Request.GET_COMMUNITIES)) {
				this.communities = (List<Community>) ResponseProcessingHelper
						.getInstance().handleResponse(Request.GET_COMMUNITIES,
								(String) response.getResponse());
				showLoginDialog();
			} else if (response.getRequestName().equals(
					Request.ADD_USER_REQUEST)
					|| response.getRequestName().equals(Request.LOGIN_ORDER)) {
				try {
					boolean added = (Boolean) ResponseProcessingHelper
							.getInstance().handleResponse(
									Request.ADD_USER_REQUEST,
									(String) response.getResponse());
					if (added) {

						if (response.getRequestName().equals(
								Request.LOGIN_ORDER)) {
							SettingDao dao = new SettingDao(
									PersistenceSessionFactory
											.createSessionInstance(activity)
											.currentSession());
							User.getInstance().getSetting().setCommunityId(communityId);
							dao.updateCommunityId(User.getInstance()
									.getCommunityId());
						}
						showHomePage();
					} else {
						closeProgress();
						Toast.makeText(
								activity.getApplicationContext(),
								activity.getResources().getString(
										R.string.connectionError),
								Toast.LENGTH_SHORT).show();
					}

				} catch (Exception ex) {

					closeProgress();
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.connectionError),
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception ex) {
			closeProgress();
			Toast.makeText(
					activity.getApplicationContext(),
					activity.getResources().getString(R.string.connectionError),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void showLoginDialog() {
		dialog = new Dialog(activity);
		dialog.setCancelable(false);
//		dialog.getWindow().getDecorView()
//				.setBackgroundResource(android.R.color.transparent);
		dialog.setContentView(R.layout.login_dialog_layout);
		dialog.setTitle(activity.getResources().getText(
				R.string.loginDialogTitle));

		// get the Refferences of views
		final EditText editTextUserName = (EditText) dialog
				.findViewById(R.id.editTextUserNameToLogin);
		final EditText editTextPassword = (EditText) dialog
				.findViewById(R.id.editTextPasswordToLogin);

		Spinner communitiesSpinner = (Spinner) dialog
				.findViewById(R.id.selctCommunity);
		List<String> items = new ArrayList<String>();
		if (communities != null) {
			for (final Community com : communities) {
				items.add(com.getCommunityName());
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, items);
		communitiesSpinner.setAdapter(adapter);
		

		final String communityId = communities != null && communities.size() > 0 ? communities.get(
				communitiesSpinner.getSelectedItemPosition()).getId() : "";
				
		Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// get The User name and Password
				if (!"".equals(editTextUserName.getText().toString())
						&& !"".equals(editTextPassword.getText().toString()) 
						&& !"".equals(communityId)) {
					String email = editTextUserName.getText().toString();
					String password = editTextPassword.getText().toString();
					LoginScreen.this.communityId = communityId;
					logIn(email, password);
				} else {
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.completeYouData),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		Button btnSignUp = (Button) dialog.findViewById(R.id.buttonSignup);
		// Set On ClickListener
		btnSignUp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				showSignUpDialog();
			}
		});
		final Button forgotButton = (Button) dialog
				.findViewById(R.id.buttonForgetPassword);
		// Set On ClickListener
		forgotButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showForgotPasswordDialog();
			}
		});

		final Button changePassword = (Button) dialog
				.findViewById(R.id.buttonChangePassword);
		// Set On ClickListener
		changePassword.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showChangePasswordDialog();
			}
		});
		final Button cancel = (Button) dialog
				.findViewById(R.id.buttonSignInCancel);
		// Set On ClickListener
		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				SettingDao dao = new SettingDao(
						PersistenceSessionFactory
								.createSessionInstance(activity)
								.currentSession());
				dao.updateCommunityId(User.EMPTY_COMMUNITY);
				User.getInstance().getSetting().setCommunityId(User.EMPTY_COMMUNITY);
				register("", "");
			}
		});

		dialog.show();
	}

	private void showSignUpDialog() {
		dialog = new Dialog(activity);
		dialog.setCancelable(false);
//		dialog.getWindow().getDecorView()
//				.setBackgroundResource(R.drawable.home_bg);
		dialog.setContentView(R.layout.register_dialog_layout);
		dialog.setTitle(activity.getResources().getText(R.string.register));

		// get the Refferences of views
		final EditText editTextUserName = (EditText) dialog
				.findViewById(R.id.editTextUserNameToLogin);

		Spinner communitiesSpinner = (Spinner) dialog
				.findViewById(R.id.selctCommunity);
		List<String> items = new ArrayList<String>();
		if (communities != null) {
			for (final Community com : communities) {
				items.add(com.getCommunityName());
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, items);
		communitiesSpinner.setAdapter(adapter);

		Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignup);

		final String communityId = communities != null && communities.size() > 0 ? communities.get(
				communitiesSpinner.getSelectedItemPosition()).getId() : "";
		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email = editTextUserName.getText().toString();
//				if (!"".equals(communityId)) {
//					User.getInstance().setCommunityId(communityId);
//					User.getInstance().setCommunityEmail(email);
//				}
				if (!"".equals(editTextUserName.getText().toString()) && !"".equals(communityId)) {
					register(communityId, email);
				} else {
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.completeYouData),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		

		final Button cancel = (Button) dialog
				.findViewById(R.id.buttonSignupCancel);
		// Set On ClickListener
		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				SettingDao dao = new SettingDao(
						PersistenceSessionFactory
								.createSessionInstance(activity)
								.currentSession());
				dao.updateCommunityId(User.EMPTY_COMMUNITY);
				User.getInstance().getSetting().setCommunityId(User.EMPTY_COMMUNITY);
				register("", "");
			}
		});

		dialog.show();
	}

	private void showForgotPasswordDialog() {
		forgotPasswordDialog = new Dialog(activity);
		forgotPasswordDialog.setCancelable(false);
//		dialog.getWindow().getDecorView()
//				.setBackgroundResource(android.R.color.transparent);
		forgotPasswordDialog.setContentView(R.layout.forgot_password_dialog_layout);
		forgotPasswordDialog.setTitle(activity.getResources()
				.getText(R.string.getMyPassTitle));

		// get the Refferences of views
		final EditText editTextUserName = (EditText) forgotPasswordDialog
				.findViewById(R.id.editTextUserNameToLogin);

		Button btnSignIn = (Button) forgotPasswordDialog
				.findViewById(R.id.buttonForgotPassword);
		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!"".equals(editTextUserName.getText().toString())) {
					forgotPassword(editTextUserName.getText().toString());
				} else {
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.completeYouData),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		final Button cancel = (Button) forgotPasswordDialog.findViewById(R.id.buttonCancel);
		// Set On ClickListener
		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				forgotPasswordDialog.dismiss();
			}
		});

		forgotPasswordDialog.show();
	}

	private void showChangePasswordDialog() {
		changePasswordDialog = new Dialog(activity);
		changePasswordDialog.setCancelable(false);
//		dialog.getWindow().getDecorView()
//				.setBackgroundResource(android.R.color.transparent);
		changePasswordDialog.setContentView(R.layout.change_password_dialog_layout);
		changePasswordDialog.setTitle(activity.getResources().getText(
				R.string.changeMyPassword));

		// get the Refferences of views
		final EditText oldPassword = (EditText) changePasswordDialog
				.findViewById(R.id.oldPassword);
		final EditText newPassword = (EditText) changePasswordDialog
				.findViewById(R.id.newPassord);
		final EditText retypeNewPassword = (EditText) changePasswordDialog
				.findViewById(R.id.retypeNewPassord);

		Button btnSignIn = (Button) changePasswordDialog.findViewById(R.id.buttonSubmit);

		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(oldPassword.getText().toString())
						|| "".equals(newPassword.getText().toString())
						|| "".equals(retypeNewPassword.getText().toString())) {
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.completeYouData),
							Toast.LENGTH_SHORT).show();
				}else if(!newPassword.getText().toString().equals(retypeNewPassword.getText().toString())){
					Toast.makeText(
							activity.getApplicationContext(),
							activity.getResources().getString(
									R.string.passwordDoesnnotMatch),
							Toast.LENGTH_SHORT).show();
				}else{
					changePassword(oldPassword.getText().toString(), newPassword.getText().toString());
				}
			}
		});

		final Button cancel = (Button) changePasswordDialog.findViewById(R.id.buttonCancel);
		// Set On ClickListener
		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				changePasswordDialog.dismiss();
			}
		});
		changePasswordDialog.show();
	}

	private void logIn(String email, String password) {
		closeProgress();
		showProgressBar();
		OrderCoordinator.handleOrder(this,
				RequestFactory.createCommunityLoginRequest(email, password));
	}

	private void register(String communityId, String CommunityEmail) {
		closeProgress();
		showProgressBar();
		OrderCoordinator.handleOrder(this, RequestFactory.createLoginRequest(
				User.getInstance().getProfile(), GCMIntentService.REG_ID,
				UserUtils.getInstalledUseEmail(activity), communityId,
				CommunityEmail));
	}

	private void forgotPassword(String email) {
		closeProgress();
		showProgressBar();
		OrderCoordinator.handleOrder(this,
				RequestFactory.createForgotPasswordRequest(email));
	}

	private void changePassword(String oldPassword, String newPassword) {
		closeProgress();
		showProgressBar();
		OrderCoordinator.handleOrder(this, RequestFactory
				.createChangePasswordRequest(oldPassword, newPassword));
	}

	private void showHomePage() {
		closeProgress();
		if(dialog != null){
			dialog.dismiss();
		}
		Intent intent = new Intent(activity, MainActivity.class);
		intent.putExtra(MainActivity.nextScreenParam, MainActivity.HOME_ID);
		activity.startActivity(intent);
	}

}
