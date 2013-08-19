package se.kronosoft.crazyparanoidclient;

import java.util.StringTokenizer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class ActionHandler {
	
	private String alias;
	private String response;
	private String data;
	private Context ctx;
	
	public ActionHandler(String alias, String response, String data, Context ctx) {
		this.alias = alias;
		this.response = response;
		this.data = data;
		this.ctx = ctx;
	}

	public void run() {

		if (response.equals("camera")) {
			cameraAction();
		}else if (response.equals("screenshot")) {
			screenShotAction();
		}else if (response.equals("gps_pos")) {
			gpsPosAction();
		}else if (response.equals("rec_sound")) {
			recSoundAction();
		}else if (response.equals("play_sound")) {
			playSoundAction();
		}else {
			//Unknown action
		}
	}

	private void playSoundAction() {
		// TODO Just make a notification or toast that sound has been played
		
	}

	private void recSoundAction() {
		// TODO Auto-generated method stub
		
	}

	private void gpsPosAction() {
		/*
		StringTokenizer sToken = new StringTokenizer(data, ";");
		
		String accuracy = sToken.nextToken();
		String latitude = sToken.nextToken();
		String longitude = sToken.nextToken();
		
		Log.e("accu", accuracy);
		Log.e("lat", latitude);
		Log.e("lon", longitude);
		
		latitude = latitude.replace(',', '.');
		longitude = longitude.replace(',', '.');
		
		String uri = "geo:"+ latitude + "," + longitude + "?22";*/
		
		//Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//ctx.startActivity(i);
		
		Intent intent = new Intent(ctx, MapActivity.class);
		intent.putExtra("data", data);
		intent.putExtra("alias", alias);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
		
	}

	private void screenShotAction() {
		// TODO Auto-generated method stub
		
	}

	private void cameraAction() {
		// TODO Auto-generated method stub
		
	}

}
