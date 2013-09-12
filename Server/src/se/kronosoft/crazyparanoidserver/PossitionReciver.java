package se.kronosoft.crazyparanoidserver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class PossitionReciver {

	private LocationManager locationManager = null;
	private Context ctx;
	private String regid;

	public PossitionReciver(Context ctx, String regid) {
		this.ctx = ctx;
		this.regid = regid;
		
		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){			
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			Log.e("PossitionReciver constr", "GPS is enabled");
		}else {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			Log.e("PossitionReciver constr", "GPS is disabled");
		}

		
		/*Criteria criteria = new Criteria();

	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    /*criteria.setSpeedRequired(false);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setCostAllowed(true);
	    criteria.setPowerRequirement(Criteria.POWER_HIGH);	
		

		
		String best = locationManager.getBestProvider(criteria, false);
		
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		if(location != null){
			Log.e("Location" , location.toString());
		} */
		
		//locationManager.requestLocationUpdates(best, 0, 0, locationListener);
		
		/*Log.e("Crit", criteria.toString());
		Log.e("Provicer", best);
		
		Log.e("LocationManager.NETWORK_PROVIDER", LocationManager.NETWORK_PROVIDER);
		Log.e("LocationManager.GPS_PROVIDER", LocationManager.GPS_PROVIDER);*/
		
	}

	LocationListener locationListener = new LocationListener() {
		// TODO! Check locale problems
		@SuppressLint("DefaultLocale")
		// For now. Should check that it doesn't cause problems
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location
			// provider. makeUseOfNewLocation(location);

			String acc = String.format("%f", location.getAccuracy());
			
			Time now = new Time();
			now.setToNow();
			//TODO! Use format instead to make the timestring look better.
			String time = now.format2445();
			String lat = String.format("%f", location.getLatitude());
			String lon = String.format("%f", location.getLongitude());
			
			Log.e("onLocationChanged", acc + ";" + time + ";" + lat + ";" + lon);
			
			Log.e("onLocationChanged", "Provider " + location.getProvider());
						
			if(regid.equals("0")){
				//Save to database!
				
				//Log.e("onLocationChanged", "regid == 0");
				
				DBHelperTool db = new DBHelperTool(ctx);
				
				db.insertGpsPos(acc + ";" + time + ";" + lat + ";" + lon, time);
				
				
			}else{
				new SendGcmPost("gps_pos",
						acc + ";" + time + ";" + lat + ";" + lon, regid, ctx)
						.execute(null, null, null);
			}
			
			locationManager.removeUpdates(locationListener);
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			Log.e("onStatusChanged", "Running");
		}

		public void onProviderEnabled(String provider) {
			Log.e("onProviderEnabled", "Running");
		}

		public void onProviderDisabled(String provider) {
			Log.e("onProviderDisabled", "Running");
		}
	};
}
