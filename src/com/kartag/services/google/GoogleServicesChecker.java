package com.kartag.services.google;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.kartag.services.ServiceChecker;

public class GoogleServicesChecker implements ServiceChecker{

	@Override
	public boolean isAvailable(Context context) {
		// TODO Auto-generated method stub
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
	}

}
