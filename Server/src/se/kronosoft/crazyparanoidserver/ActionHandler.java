package se.kronosoft.crazyparanoidserver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
			new SendGcmPost("Denial unimplemented", "0", regid, ctx).execute(null, null, null);
			//new PhotoGrabber(ctx).grab();
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}
	}

	private void screenShotAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_SCREENSHOTS, false)){
			new SendGcmPost("Denial unimplemented", "0", regid, ctx).execute(null, null, null);
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}	
	}

	private void getPosAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_GPSUSAGE, false)){
			if(prefs.getBoolean(MainActivity.PREFS_CONT_GPS, false)){
				Cursor result = new DBHelperTool(ctx).getGpsPos();
				
				if(result.moveToFirst()){
					
					String data = result.getString(2);
					
					while(result.moveToNext()){
						String toAdd = result.getString(2);
						if(data.length() + toAdd.length() < 1500){
							//Making sure that data isnt to long. Post limit 2k
							data = data + ";" + toAdd;
						}else{
							break;
						}
					}
					
					new SendGcmPost("gps_pos", data, regid, ctx).execute(null, null, null);
				}else {
					//Database empty!
					new PossitionReciver(ctx, regid);
				}
			}else{				
				new PossitionReciver(ctx, regid);
			}
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}	
	}

	private void recSoundAction() {
		//Check if setting is to allow the specified action
		if(prefs.getBoolean(MainActivity.PREFS_REGID, false)){
			new SendGcmPost("Denial unimplemented", "0", regid, ctx).execute(null, null, null);
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
		    
		    new SendGcmPost("Alarm set to "  + hour.toString() + ":" + minute.toString(), "0", regid, ctx).execute(null, null, null);
		}else{
			new SendGcmPost("Denial bad request type", "0", regid, ctx).execute(null, null, null);
		}
	}
}