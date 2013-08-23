package se.kronosoft.crazyparanoidserver;

import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class PhotoGrabber {

	private Camera camera = null;
	private Context ctx;
	SurfaceHolder surfaceHolder = null;
	

	public PhotoGrabber(Context ctx) {
		this.ctx = ctx;
	}

	public void grab() {
		
		int numOfCameras = Camera.getNumberOfCameras();
		
		if(numOfCameras == 0){
			//unit has no cameras!!
			//Notify
			return;
		}
		
		while(numOfCameras-- > 0){
			
			camera = Camera.open(numOfCameras);	
			//TODO! Get params and set params
			
			SurfaceView dummy=new SurfaceView(ctx);
			
			try{
				
				//TODO! Check http://stackoverflow.com/questions/10775942/android-sdk-get-raw-preview-camera-image-without-displaying-it/10776349#10776349
				//To se if its possible
				camera.setPreviewDisplay(dummy.getHolder());    
				camera.startPreview();
				camera.takePicture(null, null, new PhotoHandler(ctx));
			}catch(IOException e){
				
			}catch (RuntimeException e) {
				Log.e("Take pic failed", e.getLocalizedMessage());
			}
			finally{
				camera.release();
			}
		}
	}
		
		
		
		/*
		if (!ctx.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(ctx, "No camera on this device", Toast.LENGTH_LONG)
					.show();
		} else {
			cameraId = findFrontFacingCamera();
			if (cameraId < 0) {
				Toast.makeText(ctx, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
			} else {
				camera = Camera.open(cameraId);
			}
		}
		
		if(camera != null){
			camera.takePicture(null, null, new PhotoHandler(ctx));
		}
	}

	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}*/

}
