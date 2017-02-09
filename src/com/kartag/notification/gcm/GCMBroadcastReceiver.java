package com.kartag.notification.gcm;

import android.content.Context;

public class GCMBroadcastReceiver extends
		com.google.android.gcm.GCMBroadcastReceiver {

	@Override
	protected String getGCMIntentServiceClassName(Context context) {

		return "com.kartag.notification.gcm.GCMIntentService";
	}
	
}
