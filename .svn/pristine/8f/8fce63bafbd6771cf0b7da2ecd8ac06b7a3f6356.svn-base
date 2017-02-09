package com.kartag.notification.gcm;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService{

	// Google project id
    public static final String SENDER_ID = "343937923074";
 
 
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.kartag.gui.DISPLAY_MESSAGE";
 
    public static final String EXTRA_MESSAGE = "message";
    
    public static String REG_ID = "";
    
    private static final String TAG = "GCMIntentService";
    
    /**
      * Method called on device registered
      **/
     @Override
     protected void onRegistered(Context context, String registrationId) {
      Log.i(TAG, "Device registered: regId = " + registrationId);
//      REG_ID = GCMRegistrar.getRegistrationId(this);
//      if(!"".equals(REG_ID)){
//    	  GCMRegistrar.setRegisteredOnServer(this, true);
//      }
      Log.d("NAME", "KarTag");
     }
    
     /**
      * Method called on device un registred
      * */
     @Override
     protected void onUnregistered(Context context, String registrationId) {
      Log.i(TAG, "Device unregistered");
     }
   
     /**
      * Method called on Receiving a new message
      * */
     @Override
     protected void onMessage(Context context, Intent intent) {
      Log.i(TAG, "Received message");
      String message = intent.getExtras().getString(EXTRA_MESSAGE);
      generateNotification(context, message);
     }
    
     /**
      * Method called on receiving a deleted message
      * */
     @Override
     protected void onDeletedMessages(Context context, int total) {
      Log.i(TAG, "Received deleted messages notification");
     }
    
     /**
      * Method called on Error
      * */
     @Override
     public void onError(Context context, String errorId) {
      Log.i(TAG, "Received error: " + errorId);
     }
    
     @Override
     protected boolean onRecoverableError(Context context, String errorId) {
      // log message
      Log.i(TAG, "Received recoverable error: " + errorId);
      return super.onRecoverableError(context, errorId);
     }
    
     /**
      * Issues a notification to inform the user that server has sent a message.
      */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
      int icon = R.drawable.ic_launcher;
      long when = System.currentTimeMillis();
      NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
      Notification notification = new Notification(icon, message, when);
      String title = context.getString(R.string.app_name);
      
      Intent notificationIntent = new Intent(context, MainActivity.class);
      // set intent so it does not start a new activity
      notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
        Intent.FLAG_ACTIVITY_SINGLE_TOP);
      PendingIntent intent =
        PendingIntent.getActivity(context, 0, notificationIntent, 0);
      
      notification.setLatestEventInfo(context, title, message, intent);
      notification.flags |= Notification.FLAG_AUTO_CANCEL;
      
      // Play default notification sound
      notification.defaults |= Notification.DEFAULT_SOUND;
      
      // Vibrate if vibrate is enabled
      notification.defaults |= Notification.DEFAULT_VIBRATE;
      notificationManager.notify(0, notification);
     }
}
