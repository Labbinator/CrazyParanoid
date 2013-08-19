package se.kronosoft.crazyparanoidclient;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
  private GoogleMap map;
  private LatLng latestPos = null;

@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);

    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();

    Intent intent = getIntent();
    String data = intent.getStringExtra("data");
    String alias = intent.getStringExtra("alias");   
    
    StringTokenizer stringT = new StringTokenizer(data, ";"); 
    
    while(stringT.hasMoreTokens()){
    	
    	String acc = stringT.nextToken();
    	String time = stringT.nextToken();
    	String lat = stringT.nextToken();
    	String lon = stringT.nextToken();
    	    	
    	lat = lat.replace(',', '.');
    	lon = lon.replace(',', '.');
    	
    	latestPos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
    	
    	map.addMarker(new MarkerOptions().position(latestPos)
    	        .title(alias).snippet("Accuracy= "+ acc + "m\n" + time ));
    }
    
	    // Move the camera instantly to the last pos in the list with a zoom of 15.
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latestPos, 8));
	
	    // Zoom ??in??, animating the camera.
	    map.animateCamera(CameraUpdateFactory.zoomTo(14), 3000, null);
  }
} 


/*if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1){
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
        .title("Hamburg"));
    Marker kiel = map.addMarker(new MarkerOptions()
        .position(KIEL)
        .title("Kiel")
        .snippet("Kiel is cool")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));

    // Move the camera instantly to hamburg with a zoom of 15.
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
}else{
	//THIS DOESENT WORK!!!
	map = new MapView(this).getMap();
	
    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
	        .title("Hamburg"));
	    Marker kiel = map.addMarker(new MarkerOptions()
	        .position(KIEL)
	        .title("Kiel")
	        .snippet("Kiel is cool")
	        .icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.ic_launcher)));
	
	    // Move the camera instantly to hamburg with a zoom of 15.
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
	
	    // Zoom in, animating the camera.
	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);    
}*/
