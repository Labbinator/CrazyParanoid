package se.kronosoft.crazyparanoidserver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.AlarmClock;
import android.text.format.Time;
import android.util.Log;

public class ActionHandler {
	
	private String action;
	private String passw;
	private String regid;
	private SharedPreferences prefs;
	private Context ctx;
	
	public ActionHandler(String action, String passw, String regid, Context context){
		this.action = action;
		this.passw = passw;
		this.regid = regid;
		ctx = context;
		
		prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	public void run() {
		
		if(!passw.equals(prefs.getString(MainActivity.PREFS_PASSW, "")) && passw.length() != 0){
			//Attempt with wrong pass!		
			new SendGcmPost("Denial password", "0", regid, ctx).execute(null, null, null);
			
			return;
		}
		
		
		Log.e("run", "Incoming req:" + action + " \nPass: " + passw);
		
		if (action.equals("camera")) {
			cameraAction();
		}else if (action.equals("screenshot")) {
			screenShotAction();
		}else if (action.equals("get_possition")) {
			getPosAction();
		}else if (action.equals("rec_sound")) {
			recSoundAction();
		}else if (action.equals("play_sound")) {
			playSoundAction();
		}else {
			//Unknown action
			new SendGcmPost("Denial unknown request type", "0", regid, ctx).execute(null, null, null);
			return;
		}
	}

	private void cameraAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_CAMERA, false)){
			
			new PhotoGrabber(ctx).grab();
			
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}
	}

	private void screenShotAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_SCREENSHOTS, false)){
			
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}	
	}

	private void getPosAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_GPSUSAGE, false)){
			
			new PossitionReciver(ctx, regid);
			
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}	
	}

	private void recSoundAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_REGID, false)){
			
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}
	}

	private void playSoundAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_PLAYSOUND, false)){
			
			Time t = new Time();
			t.setToNow();
			
			Integer hour = Integer.parseInt(t.format("%H"));
			Integer minute = Integer.parseInt(t.format("%M"));
			
			if(minute == 59){
				minute = 0;
				hour++;
			}else{
				minute++;
			}
			
			if(hour == 24){
				hour = 0;
			}
			
		    Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
		    i.putExtra(AlarmClock.EXTRA_HOUR, hour);
		    i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
		    i.putExtra(AlarmClock.EXTRA_MESSAGE, "Crazy Paranoid Alarm");
		    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    ctx.startActivity(i);
		    
		    //TODO! Make the system respond that an alarm is set.
		    
			
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}
	}
}
