package com.kartag.notification;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.client.processing.ResponseProcessingHelper;
import com.kartag.controller.OrderCoordinator;
import com.kartag.controller.RequestFactory;
import com.kartag.gui.IActivity;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.persistence.model.bo.User;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class NotificationHandler implements IActivity {

	private Handler updateHandler = new Handler();
	private static NotificationHandler self = null;
	private FragmentActivity activity;
	private Runnable mUpdateTimeTask;
	private static long executionInterval = 1000 * 60 * 5;
	//private static boolean started = false;

	private NotificationHandler(FragmentActivity activity) {
		this.activity = activity;
	}

	public static NotificationHandler getInstance(FragmentActivity activity) {
		if (self == null) {
			self = new NotificationHandler(activity);
		}
		return self;
	}

	public void startNotificationHandler() {

			mUpdateTimeTask = new Runnable() {
				public void run() {
					try {
						OrderCoordinator
								.handleOrder(self, RequestFactory
										.createGetUserUdatesCountRequest(User
												.getInstance().getProfile()
												.getId()));
						

					} catch (Exception ex) {

					}

				}
			};
			new Thread(mUpdateTimeTask).start();

	}

	private void createNotification() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				activity).setSmallIcon(R.drawable.logo)
				.setContentTitle("My notification")
				.setContentText("Hello World!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(activity, MainActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) activity
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		// mNotificationManager.notify(mId, mBuilder.build());
	}

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postExecution(Response response) {
		// TODO Auto-generated method stub
		try
		{
		if (response.getRequestName().equals(Request.GET_MESSAGES_NOTIFICATION_COUNT_REQUEST)) {
			
			String[] newNotifications = (String[]) ResponseProcessingHelper.getInstance()
					.handleResponse(Request.GET_MESSAGES_NOTIFICATION_COUNT_REQUEST,
							(String) response.getResponse());
//			String[] newNotifications = result.split(",");
			int messagesCount = (newNotifications != null && newNotifications.length == 3) ? Integer
					.parseInt(newNotifications[0]) : 0;
			int notificationsCount = (newNotifications != null && newNotifications.length == 3) ? Integer
					.parseInt(newNotifications[1]) : 0;
			int repliesCount = (newNotifications != null && newNotifications.length == 3) ? Integer
					.parseInt(newNotifications[2]) : 0;
//			if (messagesCount > 0 && notificationsCount > 0
//					&& repliesCount > 0) {
//				Toast.makeText(
//						activity.getApplicationContext(),
//						"You have " + messagesCount
//								+ " new messages and "
//								+ notificationsCount
//								+ " new notifications and "
//								+ repliesCount + " new replies",
//						Toast.LENGTH_LONG).show();
//			} else if (messagesCount > 0) {
//				Toast.makeText(
//						activity.getApplicationContext(),
//						"You have " + messagesCount
//								+ " new messages",
//						Toast.LENGTH_LONG).show();
//
//			} else if (repliesCount > 0) {
//				Toast.makeText(
//						activity.getApplicationContext(),
//						"You have " + notificationsCount
//								+ " new reply", Toast.LENGTH_LONG)
//						.show();
//
//			} else if (notificationsCount > 0) {
//				Toast.makeText(
//						activity.getApplicationContext(),
//						"You have " + notificationsCount
//								+ " new notifications",
//						Toast.LENGTH_LONG).show();
//
//			}
			User.getInstance().setNotifications(notificationsCount);
			//started = true;
//			Log.i("Notification Handler started", String.valueOf(newNotifications.length));
			updateHandler.postDelayed(mUpdateTimeTask, executionInterval);
		}
		}
		catch(Exception ex)
		{
			Log.e("ERROR", ex.getMessage());
		}
		
	}

	// private void updateNotification()
	// {
	// mNotificationManager =
	// (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	// // Sets an ID for the notification, so it can be updated
	// int notifyID = 1;
	// mNotifyBuilder = new NotificationCompat.Builder(this)
	// .setContentTitle("New Message")
	// .setContentText("You've received new messages.")
	// .setSmallIcon(R.drawable.ic_notify_status)
	// numMessages = 0;
	// // Start of a loop that processes data and then notifies the user
	//
	// mNotifyBuilder.setContentText(currentText)
	// .setNumber(++numMessages);
	// // Because the ID remains unchanged, the existing notification is
	// // updated.
	// mNotificationManager.notify(
	// notifyID,
	// mNotifyBuilder.build());
	// }

}
