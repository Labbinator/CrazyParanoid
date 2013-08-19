package se.kronosoft.crazyparanoidserver;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
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

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 1, 0, locationListener);
	}

	LocationListener locationListener = new LocationListener() {
		// TODO! Check locale problems
		@SuppressLint("DefaultLocale")
		// For now. Should check that it doesent couse problems
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

			new SendGcmPost("gps_pos",
					acc + ";" + time + ";" + lat + ";" + lon, regid, ctx)
					.execute(null, null, null);

			locationManager.removeUpdates(locationListener);
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};
}
