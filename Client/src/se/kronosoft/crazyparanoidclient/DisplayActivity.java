package se.kronosoft.crazyparanoidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		TextView tv = (TextView)findViewById(R.id.displayTextView);
		
		Intent i = getIntent();
		String s = i.getStringExtra("extra");
		tv.setText(s);
	}
}
