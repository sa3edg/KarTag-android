package com.kartag.util;

import com.kartag.persistence.model.bo.Trip;


public class TripUtils {

	public static String getPublishedTripToWall(Trip trip)
	{
		if(trip == null)
			return "";
		String publishedTrip = "";
		if(trip.getType().equals(Trip.OFFER))
		{
			publishedTrip = "Kartag.com: I will drive from "+ trip.getStartPool().getPoolName() + 
					" to "+ trip.getEndPool().getPoolName() + " at " + trip.getTime() +
					", If you want to join to me install kartag then send me request.";
		} else if(trip.getType().equals(Trip.REQUEST))
		{
			publishedTrip = "Kartag.com: I wil going from "+ trip.getStartPool().getPoolName() + 
					" to "+ trip.getEndPool().getPoolName() + " at " + trip.getTime() +
					", If you have a ride install kartag then send me request.";
		}
		return publishedTrip;
	}
}
