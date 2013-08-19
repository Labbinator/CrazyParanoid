package se.kronosoft.crazyparanoidclient;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterGcmBackground extends AsyncTask<Void, Void, String> {

	private Context ctx;
	private String regid;
	private SharedPreferences prefs;
	private GoogleCloudMessaging gcm;

	public RegisterGcmBackground(Context context) {
		ctx = context;

		prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		
		gcm = GoogleCloudMessaging.getInstance(ctx);
	}

	@Override
	protected String doInBackground(Void... params) {
		String msg = "Failed";
		try {
			regid = gcm.register(MainActivity.SENDER_ID);
			Log.e("doInBackground", regid);
			msg = "Device registered, registration id=" + regid;
		} catch (Exception e) {
			Log.e("doInBackground", "Error: " + e.getMessage());
		}
		return msg; // Returnerar meddelandet till onPostExecute
	}

	@Override
	protected void onPostExecute(String msg) {
		// TODO!! Visa för användaren att appen är reggad
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(MainActivity.PREFS_REGID, regid);
		editor.commit();
	}
}
