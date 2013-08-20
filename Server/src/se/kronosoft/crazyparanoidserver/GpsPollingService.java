package se.kronosoft.crazyparanoidserver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class GpsPollingService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// Check prefs!
		SharedPreferences prefs;
		prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		prefs.getBoolean(MainActivity.PREFS_CONT_GPS, false);
		
		
		/* Vogella alarm manager set example
		Calendar cal = Calendar.getInstance();

		Intent intent = new Intent(this, MyService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent); 
		
		*/

		return null;
	}

	
	

}
