package com.kartag.client.processing;


import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.gui.IActivity;

public  abstract class AbstractProcessor  implements IProcessor {

	protected Request request;
	protected Response response = new Response();
	protected IActivity activity;
    protected final String API_URL = "http://10.0.2.2:8888/KarTagServer/processOrder.do";
	//protected final String API_URL = "http://www.kartag.com:8080/KarTagServer/processOrder.do";

	public void process() throws Exception{
		AsyncSimpleConnection conn = new AsyncSimpleConnection(this);
		conn.execute(new String[] { API_URL });
	}

	public void preprocess() {
		response.setRequestName(request.getRequestName());
		if(!request.isAsync())
		{
			if(activity != null)
			{
			   activity.preExecution();
			}
		}
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;

	}

	public void terminate() {

	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public void setActivity(IActivity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
	}

	public IActivity getActivity() {
		// TODO Auto-generated method stub
		return this.activity;
	}
}
