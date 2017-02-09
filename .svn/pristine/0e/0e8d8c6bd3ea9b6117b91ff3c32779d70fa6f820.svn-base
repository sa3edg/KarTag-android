package com.kartag.client.common;

import com.kartag.client.processing.IProcessor;

public interface IRequest extends RequestName, RequestParameter, RequestActions {
	public static final String SOURCE_BROWSEER = "browser";
	public static final String SOURCE_DEVICE_ANDROID = "android";
	public static final String SOURCE_DEVICE_MOBILE = "mobile";

	public static final String APP_KEY_ANDROID = "android_key";
	public static final String APP_KEY_MOBILE = "mobile_key";

	public void setAuthenticate(boolean authenticated);

	public boolean getAuthenticate();

	public IProcessor getProcessor();

	public void setProcessor(IProcessor processor);

	/**
	 * The return value indicate if at the present time (asynchronous) responses
	 * are expected.
	 * 
	 * @return a boolean indicating if more responses are expected.
	 */
	boolean isStoreResponse();

	/**
	 * sets store response
	 * 
	 */
	void setStoreResponse(boolean store);
	
	/**
	 * sets the value if  the request is (asynchronous) 
	 * 
	 */
	void setAsync(boolean isAsync);

}
