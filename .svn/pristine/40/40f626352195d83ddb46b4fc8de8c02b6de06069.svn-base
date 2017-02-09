package com.kartag.client.processing;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import android.app.Activity;

public class TwitterProcessor extends AbstractProcessor {
	private Activity mainActivity;
	private String postedMessage = "";
	private static final String TOKEN ="440823737-Azo1E20SEP31UsYAwsYmbvICvPauwWWcDsOC0aus"; 
	private static final String SECRET = "ZIea3wbxXh7HNkBbGcHGFwJIYXzMLJngo4FNAA9hw";
	
	private static final String CONSUMER_KEY ="ITVVZ7vJF2LX1n9nUbHaxw"; 
	private static final String CONSUMER_SECRET = "Xs4udiJoIIxjF7rsQB1DaiYw0VLLr9NnjCLbeBl3Xk";
	public void process() throws Exception{

		if (request.getRequestName().equals(com.kartag.client.common.Request.FB_LOGIN_ORDER)) {
			postToTwitterHashTag();
		}
	}

	private void postToTwitterHashTag() throws Exception
	{
		AccessToken accessToken = new AccessToken(TOKEN, SECRET);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        twitter.setOAuthAccessToken(accessToken);
        try {
            twitter.updateStatus(getPostedMessage());
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public void preprocess() {
		super.preprocess();
	}

	public void terminate() {
		super.terminate();
	}
	
	
	public Activity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public String getPostedMessage() {
		return postedMessage;
	}

	public void setPostedMessage(String postedMessage) {
		this.postedMessage = postedMessage;
	}

}
