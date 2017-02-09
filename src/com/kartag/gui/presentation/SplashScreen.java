package com.kartag.gui.presentation;



import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class SplashScreen extends AbstractScreen {
	
    protected boolean _active = true;
    protected int _splashTime = 2000;
    
    public SplashScreen(FragmentActivity activity)
	{
		super(activity);
	}
	
    
	@Override
	public void loadScreen() {
		
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(R.layout.splash);
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                	showLoginScreen();
                	
                }
            }
        };
        splashTread.start();
	}
	private void showLoginScreen()
	{
		activity = MainActivity.finishCurrentActivity(activity);
    	Intent intent = new Intent(activity, MainActivity.class);
		intent.putExtra(MainActivity.nextScreenParam, MainActivity.LOGIN_ID);
	    activity.startActivity(intent);
		
//		Intent intent = new Intent(activity, LoginActivity.class);
//		activity.startActivity(intent);
	}
}

