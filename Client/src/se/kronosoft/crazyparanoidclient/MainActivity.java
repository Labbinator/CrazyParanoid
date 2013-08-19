package se.kronosoft.crazyparanoidclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	EditText alias;
	EditText passw;
	SharedPreferences prefs;
	public static final String PREFS_ALIAS = "alias";
	public static final String PREFS_PASSW = "passw";
	public static final String PREFS_REGID = "registration_id";
	public static final String SENDER_ID = "759880544931";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	// Sets click listeners and on text watcher
	private void init() {
		prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		findViewById(R.id.camera_row).setOnClickListener(this);
		findViewById(R.id.screenshots_row).setOnClickListener(this);
		findViewById(R.id.soundrec_row).setOnClickListener(this);
		findViewById(R.id.soundplay_row).setOnClickListener(this);
		findViewById(R.id.gpsuse_row).setOnClickListener(this);

		alias = (EditText) findViewById(R.id.aliasEditText);
		passw = (EditText) findViewById(R.id.passwEditText);

		alias.setText(prefs.getString(PREFS_ALIAS, ""));
		passw.setText(prefs.getString(PREFS_PASSW, ""));

		alias.addTextChangedListener(textWatcher);
		passw.addTextChangedListener(textWatcher);
		
		if(prefs.getString(PREFS_REGID, "").length() == 0){
			new RegisterGcmBackground(this).execute(null, null, null);
		}
	}

	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			//Makes sure the alias and password is saved after each input
			saveSettings();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	private void saveSettings() {
		SharedPreferences.Editor editor = prefs.edit();

		editor.putString(PREFS_ALIAS, alias.getText().toString());
		editor.putString(PREFS_PASSW, passw.getText().toString());
		editor.commit();
	}

	@Override
	public void onClick(View v) {

		String message = "";

		switch (v.getId()) {
		case R.id.camera_row:
			message = "camera";
			break;
		case R.id.screenshots_row:
			message = "screenshot";
			break;
		case R.id.soundrec_row:
			message = "rec_sound";
			break;
		case R.id.soundplay_row:
			message = "play_sound";
			break;
		case R.id.gpsuse_row:
			message = "get_possition";
			break;
		default:// Should never happen!
			break;
		}

		if (message.length() != 0) {
			new SendGcmPost(message, this).execute(null, null, null);
		}
	}
}
