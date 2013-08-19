package se.kronosoft.crazyparanoidserver;

import se.kronosoft.crazyparanoidserver.MainActivity;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmBroadcastReciver extends BroadcastReceiver {

	static final String TAG = "CrazyParanoid";
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	SharedPreferences prefs;
	Context ctx;

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(ctx);

		prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);

		String messageType = gcm.getMessageType(intent);
		if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
			handleMessage(intent);
		} else {
			// This should not happen...
			// sendNotification("GCM message error: " +
			// intent.getExtras().toString());
		}
		setResultCode(Activity.RESULT_OK);
	}

	public void handleMessage(Intent intent) {
		
		//Get stuff from intent
		String pass = intent.getStringExtra("passw"); 
		String action = intent.getStringExtra("req_type");
		String regid = intent.getStringExtra("regid");
		
		if(action != null && action.equals("RegOK")){
			sendNotification("Server registration OK!");
			return;
		}
		
		if(pass == null || action == null || regid == null){
			//Bad message, quitting
			Log.e("handleMessage","Null message recived");
			return;
		}
		
		if(prefs.getBoolean(MainActivity.PREFS_NOTIFICATIONS, false)){
			//Creates a notification based on settings
			sendNotification(intent.getExtras().toString());
			sendNotification("Incoming request of type: " + action);
		}
		
		ActionHandler handler = new ActionHandler(action, pass, regid, ctx);
		handler.run();
	}

	// Builds a notification.
	private void sendNotification(String msg) {
		// TODO!! Make the notification look better.
		mNotificationManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
				new Intent(ctx, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				ctx).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("CrazyParanoid")
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}
