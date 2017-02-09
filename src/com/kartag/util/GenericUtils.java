package com.kartag.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class GenericUtils {
	public static void launchURL(Activity activity, String url){
		Intent webIntent = new Intent( Intent.ACTION_VIEW );
        webIntent.setData( Uri.parse(url) );
        activity.startActivity( webIntent );
	}

}
