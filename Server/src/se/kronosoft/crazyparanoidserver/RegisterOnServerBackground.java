package se.kronosoft.crazyparanoidserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterOnServerBackground extends AsyncTask<Void, Void, String> {

	Context ctx;
	SharedPreferences prefs;
	private static final String SERVER_URL = "http://labe.ath.cx:6677/cp_gcm/register.php"; 

	public RegisterOnServerBackground(Context context) {
		ctx = context;
	}

	@Override
	protected String doInBackground(Void... arg0) {

		Map<String, String> params = new HashMap<String, String>();

		prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);

		String regid = prefs.getString(MainActivity.PREFS_REGID, "");
		String name = prefs.getString(MainActivity.PREFS_ALIAS, "");

		params.put("name", name);
		params.put("regId", regid);

		try {
			post(SERVER_URL, params);
		} catch (IOException e) {
			return "Failed";
		}

		return "Successful";
	}

	protected void onPostExecute(String msg) {
		// Visar för användaren att appen är reggad
		//Toast.makeText(ctx, "Server registration: " + msg, Toast.LENGTH_LONG)
			//	.show();
		
		 SharedPreferences prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE); 
		 SharedPreferences.Editor editor = prefs.edit(); editor.putBoolean(MainActivity.PREFS_REG_STATUS, true); 
		 editor.commit();
	}

	private static void post(String endpoint, Map<String, String> params)
			throws IOException {

		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v("post", "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			Log.e("URL", "> " + url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
