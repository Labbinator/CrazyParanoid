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

public class SendGcmPost extends AsyncTask<Void, Void, String> {

	private static final String SERVER_URL = "http://kronosoft.se/cp_gcm/sendresponse.php";
	private String response;
	private String data;
	private String regid;
	private Context ctx;

	public SendGcmPost(String response, String data, String regid, Context context) {
		this.response = response;
		this.regid = regid;
		if(data == null || data.length() == 0){
			data = "0";
		}else{
			this.data = data;			
		}
		ctx = context;
	}

	@Override
	protected String doInBackground(Void... v) {

		Map<String, String> params = new HashMap<String, String>();

		// Get alias from shared prefs
		SharedPreferences prefs = ctx.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

		String alias = prefs.getString(MainActivity.PREFS_ALIAS, "");
		
		params.put("regid", regid);
		params.put("response", response);
		params.put("alias", alias);
		params.put("data", data);
		
		try {
			post(SERVER_URL, params);
		} catch (IOException e) {
			return "Failed";
		}

		return "Successful";
	}

	protected void onPostExecute(String msg) {

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
		Log.e("post", "Posting '" + body + "' to " + url);
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
