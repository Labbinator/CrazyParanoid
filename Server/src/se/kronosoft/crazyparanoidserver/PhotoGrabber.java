package se.kronosoft.crazyparanoidserver;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.widget.Toast;

public class PhotoGrabber {

	private Camera camera = null;
	private Context ctx;
	private int cameraId = 0;

	public PhotoGrabber(Context ctx) {
		this.ctx = ctx;
	}

	public void grab() {
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
	}

}
