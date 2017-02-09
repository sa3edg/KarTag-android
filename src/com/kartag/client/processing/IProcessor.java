package com.kartag.client.processing;

import com.kartag.client.common.Request;
import com.kartag.client.common.Response;
import com.kartag.gui.IActivity;


/**
 * Interface of Processors, i.e. the classes that handle an incoming XML
 * request.
 *
 */
public interface IProcessor
{
   /**
    * Performs the actual (synchronous) processing of the request.
    * @return the result, in most cases a {@link org.jdom.Document}.
    */
   void process() throws Exception;

   /**
    * Performs preprocessing steps before the actual processing.
    *
    */
   void preprocess();
  
   
   /**
    * return processor request.
    */
   Request getRequest();
   
   /**
    * return processor response.
    */
   Response getResponse();
   
   /**
    * Supplies the processor with necessary data.
    * @param request the incoming request
    */
   void setRequest(Request request);
   
   /**
    * Supplies the processor with necessary data.
    * @param request the incoming request
    */
   void setResponse(Response response);


   /**
    * Shuts down the processor, i.e. close any connections etc.
    */
   void terminate();
   
   /**
    * Supplies the processor with necessary data.
    * @param request the incoming request
    */
   void setActivity(IActivity activity);
   
   /**
    * return processor activity.
    */
   IActivity getActivity();
  
}
