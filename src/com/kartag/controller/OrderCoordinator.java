package com.kartag.controller;

import android.util.Log;

import com.kartag.client.common.Request;
import com.kartag.client.processing.IProcessor;
import com.kartag.client.processing.ProcessorFactory;
import com.kartag.gui.IActivity;

public class OrderCoordinator {
	
	private static OrderCoordinator self = null;
	private OrderCoordinator()
	{
		
	}
	
	public static void createInstance()
	{
		if(self == null)
		{
			self = new OrderCoordinator();
		}
	}
	public static void handleOrder(IActivity activity,Request request)
	{
		IProcessor processor = null;
		try
		{
			
			processor = ProcessorFactory.create(activity,request);
			processor.preprocess();
			processor.process();
		}
		catch(Exception ex)
		{
			Log.i("Error", ex.getMessage());
		}
		finally
		{
			if(processor != null)
				processor.terminate();
		}
	}

}
