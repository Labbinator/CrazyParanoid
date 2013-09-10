package se.kronosoft.crazyparanoidclient;

import android.content.Context;
import android.content.Intent;

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
		// TODO Just make a notification that sound will be played
	}

	private void recSoundAction() {
		// TODO Auto-generated method stub
	}

	private void gpsPosAction() {
		
		//TODO! For future, save data in notification so map appears when user presses notification?
		
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
