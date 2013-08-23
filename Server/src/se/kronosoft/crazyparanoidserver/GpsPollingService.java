package se.kronosoft.crazyparanoidserver;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class GpsPollingService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.e("GpsPollingService: onStart", "Starting!");
		
		// Check prefs!
		SharedPreferences prefs;
		prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		
		if (prefs.getBoolean(MainActivity.PREFS_CONT_GPS, false)) {

			new PossitionReciver(this, "0");

			// Sätta ett alarm
			int minBetweenPolling = prefs.getInt(MainActivity.PREFS_POLL_INT,
					10);

			Calendar cal = Calendar.getInstance();

			Intent i = new Intent(this, GpsPollingService.class);
			PendingIntent pintent = PendingIntent
					.getService(this, 0, i, 0);

			AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

			alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 60
					* 1000 * minBetweenPolling, pintent);

		}
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
