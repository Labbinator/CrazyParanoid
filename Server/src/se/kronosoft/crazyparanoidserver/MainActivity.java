package se.kronosoft.crazyparanoidserver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public static final String SENDER_ID = "759880544931";
	public static final String PREFS_REGID = "registration_id";
	public static final String PREFS_SERVER_REG = "server_reg";
	public static final String PREFS_REG_STATUS = "reg_status";
	public static final String PREFS_ALIAS = "reg_name";
	public static final String PREFS_PASSW = "reg_pass";
	public static final String PREFS_CAMERA = "camera";
	public static final String PREFS_SCREENSHOTS = "screens";
	public static final String PREFS_SOUNDREC = "soundr";
	public static final String PREFS_PLAYSOUND = "plays";
	public static final String PREFS_GPSUSAGE = "gpsuse";
	public static final String PREFS_CONT_GPS = "gpspoll";
	public static final String PREFS_POLL_INT = "gpspoll_time";
	public static final String PREFS_NOTIFICATIONS = "notifications";

	private SharedPreferences prefs;

	private CheckBox camera;
	private CheckBox screenShots;
	private CheckBox soundRec;
	private CheckBox soundPlay;
	private CheckBox gpsUse;
	private CheckBox gpsPoll;
	private CheckBox notifications;
	private SeekBar pollIntSeekBar;
	private EditText pollIntEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		setClickListeners();
		setCheckBoxes();
		setPollingIntervall();
	}

	private void setPollingIntervall() {
		pollIntSeekBar = (SeekBar) findViewById(R.id.poll_int_seekBar);
		pollIntEditText = (EditText) findViewById(R.id.poll_int_editText);

		pollIntSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (fromUser) {
							Integer seek = pollIntSeekBar.getProgress() + 10;
							pollIntEditText.setText(seek.toString());
						}
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}
				});

		pollIntEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Integer tmp = Integer.parseInt(s.toString());
				pollIntSeekBar.setProgress(tmp - 10);
				saveSettings();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		pollIntEditText.setText(((Integer) prefs.getInt(PREFS_POLL_INT, 10))
				.toString());
	}

	private void setCheckBoxes() {
		camera = (CheckBox) findViewById(R.id.cameraCheckBox);
		screenShots = (CheckBox) findViewById(R.id.screenShotsCheckBox);
		soundRec = (CheckBox) findViewById(R.id.soundRecordingCheckBox);
		soundPlay = (CheckBox) findViewById(R.id.soundPlaybackCheckBox);
		gpsUse = (CheckBox) findViewById(R.id.gpsUsageCheckBox);
		gpsPoll = (CheckBox) findViewById(R.id.continousGpsCheckBox);
		notifications = (CheckBox) findViewById(R.id.reciveNotificationsCheckBox);

		camera.setChecked(prefs.getBoolean(PREFS_CAMERA, false));
		screenShots.setChecked(prefs.getBoolean(PREFS_SCREENSHOTS, false));
		soundRec.setChecked(prefs.getBoolean(PREFS_SOUNDREC, false));
		soundPlay.setChecked(prefs.getBoolean(PREFS_PLAYSOUND, false));
		gpsUse.setChecked(prefs.getBoolean(PREFS_GPSUSAGE, false));
		gpsPoll.setChecked(prefs.getBoolean(PREFS_CONT_GPS, false));
		notifications.setChecked(prefs.getBoolean(PREFS_NOTIFICATIONS, false));
	}

	private void setClickListeners() {
		findViewById(R.id.alias_pass_row).setOnClickListener(this);
		findViewById(R.id.advanced_row).setOnClickListener(this);
		findViewById(R.id.camera_row).setOnClickListener(this);
		findViewById(R.id.screenshots_row).setOnClickListener(this);
		findViewById(R.id.soundrec_row).setOnClickListener(this);
		findViewById(R.id.soundplay_row).setOnClickListener(this);
		findViewById(R.id.gpsuse_row).setOnClickListener(this);
		findViewById(R.id.gpspoll_row).setOnClickListener(this);
		findViewById(R.id.notifications_row).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.alias_pass_row:
			Intent intent = new Intent(this, SetAliasPass.class);
			startActivity(intent);
			break;
		case R.id.advanced_row:
			Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.camera_row:
			if (camera.isChecked()) {
				camera.setChecked(false);
			} else {
				camera.setChecked(true);
			}
			saveSettings();
			break;
		case R.id.screenshots_row:
			if (screenShots.isChecked()) {
				screenShots.setChecked(false);
			} else {
				screenShots.setChecked(true);
			}
			saveSettings();
			break;
		case R.id.soundrec_row:
			if (soundRec.isChecked()) {
				soundRec.setChecked(false);
			} else {
				soundRec.setChecked(true);
			}
			saveSettings();
			break;
		case R.id.soundplay_row:
			if (soundPlay.isChecked()) {
				soundPlay.setChecked(false);
			} else {
				soundPlay.setChecked(true);
			}
			saveSettings();
			break;
		case R.id.gpsuse_row:
			if (gpsUse.isChecked()) {
				gpsUse.setChecked(false);
			} else {
				gpsUse.setChecked(true);
			}
			saveSettings();
			break;
		case R.id.gpspoll_row:
			if (gpsPoll.isChecked()) {
				gpsPoll.setChecked(false);
			} else {
				gpsPoll.setChecked(true);
			}
			saveSettings();
			startGpsPollingService();
			break;
		case R.id.notifications_row:
			if (notifications.isChecked()) {
				notifications.setChecked(false);
			} else {
				notifications.setChecked(true);
			}
			saveSettings();
			break;
		default:
			break;
		}
	}

	private void startGpsPollingService() {
        Intent startServiceIntent = new Intent(this, GpsPollingService.class);
        startService(startServiceIntent);
	}

	private void saveSettings() {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(PREFS_CAMERA, camera.isChecked());
		editor.putBoolean(PREFS_SCREENSHOTS, screenShots.isChecked());
		editor.putBoolean(PREFS_SOUNDREC, soundRec.isChecked());
		editor.putBoolean(PREFS_PLAYSOUND, soundPlay.isChecked());
		editor.putBoolean(PREFS_GPSUSAGE, gpsUse.isChecked());
		editor.putBoolean(PREFS_CONT_GPS, gpsPoll.isChecked());
		editor.putInt(PREFS_POLL_INT,
				Integer.parseInt(pollIntEditText.getText().toString()));
		editor.putBoolean(PREFS_NOTIFICATIONS, notifications.isChecked());
		editor.commit();
	}
}
