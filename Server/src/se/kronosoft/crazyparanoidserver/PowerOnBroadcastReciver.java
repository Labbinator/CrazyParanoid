package se.kronosoft.crazyparanoidserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerOnBroadcastReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
        Intent startServiceIntent = new Intent(context, GpsPollingService.class);
        context.startService(startServiceIntent);
	}
}
