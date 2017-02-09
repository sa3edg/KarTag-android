package com.kartag.client.common;

import java.util.List;

import org.apache.http.NameValuePair;

import com.kartag.client.processing.IProcessor;


public class Request implements IRequest{
	
	private boolean authenticated = false;
	private String orderType = "";
	private String requestName = "";
	private String key = "";
	private IProcessor processor = null;
	private boolean storeResponse = false;
	private List<NameValuePair> parameters;
	private boolean isAsync = false;
	//system orders types
	public static final String SIMPLE_ORDER_TYPE = "SIMPLE_ORDER";
	public static final String FACEBOOK_ORDER_TYPE = "FACEBOOK_ORDER";
	
	private String source = "android";
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}


	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setAuthenticate(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public IProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(IProcessor processor) {
		
		this.processor = processor;
		
	}

	public boolean getAuthenticate() {
		return authenticated;
	}

	public boolean isStoreResponse() {
		return storeResponse;
	}

	public void setStoreResponse(boolean store) {
		
		this.storeResponse = store;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

}
