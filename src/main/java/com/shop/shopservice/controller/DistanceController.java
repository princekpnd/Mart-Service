package com.shop.shopservice.controller;

public class DistanceController {

	public double Distance(double origLat, double origLon, double desLat, double desLon) {
		double result = 0;
		try {
			double radius = 6371;
			double dlat = Math.toRadians(desLat - origLat);
			double dlon = Math.toRadians(desLon - origLon);
			double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(Math.toRadians(origLat))
					* Math.cos(Math.toRadians(desLat)) * Math.sin(dlon / 2) * Math.sin(dlon / 2);

			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double d = Math.round(radius * c * 1000);
			result = d / 1000;
			
			return result;
		} catch (Exception e) {
			return result;
		}
	}

}
