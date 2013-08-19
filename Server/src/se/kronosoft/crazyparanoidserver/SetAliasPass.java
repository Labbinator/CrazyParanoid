package se.kronosoft.crazyparanoidserver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class SetAliasPass extends Activity implements OnClickListener {

	protected String regid;
	private SharedPreferences prefs;
	EditText aliasEditText;
	EditText pass1EditText;
	EditText pass2EditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_alias_pass);

		findViewById(R.id.reg_button).setOnClickListener(this);
		findViewById(R.id.unreg_button).setOnClickListener(this);
		aliasEditText = (EditText) findViewById(R.id.aliasEditText);
		pass1EditText = (EditText) findViewById(R.id.pass1EditText);
		pass2EditText = (EditText) findViewById(R.id.pass2EditText);

		prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);

		regid = prefs.getString(MainActivity.PREFS_REGID, "");

		Log.e("onCreate", "Shared prefs id: " + regid);

		if (regid.length() != 0) {
			// TODO!! Visa för användaren att appen är reggad
			// Skapa en textruta eller liknande där det står att man är reggad
			// och under vilket namn.
		}
	}

	private void unreg() {
		Toast.makeText(this, "Method not implemented yet", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_button:
			register();
			break;
		case R.id.unreg_button:
			unreg();
			break;
		default:
			break;
		}
	}

	private void register() {
		// TODO!! Change the string length req later
		// TODO!! Break out checking of alias and pass to make sure that they
		// are good
		if (aliasEditText.getText().toString().length() > 1) {
			if (pass1EditText.getText().toString().length() > 1) {
				if (pass1EditText.getText().toString()
						.equals(pass2EditText.getText().toString())) {

					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(MainActivity.PREFS_ALIAS, aliasEditText
							.getText().toString());
					editor.putString(MainActivity.PREFS_PASSW, pass1EditText.getText().toString());
					editor.commit();

					registerGcmBackground();
				} else {
					Toast.makeText(this, "Passwords do not match.",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "Passwords to short! Min 6 chars.",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Alias to short! Min 6 chars.",
					Toast.LENGTH_LONG).show();
		}
	}

	private void registerGcmBackground() {
		new RegisterGcmBackground(this).execute(null, null, null);
		// Starts right away
		// RegisterGcmBackground starts registerServerBackground
	}
}
