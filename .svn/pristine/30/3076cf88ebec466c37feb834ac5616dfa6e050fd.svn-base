package com.kartag.notification.gcm;

import android.content.Context;
import android.os.PowerManager;
import android.view.View;

public abstract class WakeLocker {
    private static PowerManager.WakeLock wakeLock;
 
    public static void acquire(Context context) {
        if (wakeLock != null) wakeLock.release();
 
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock( View.KEEP_SCREEN_ON |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire();
    }
 
    public static void release() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
}
