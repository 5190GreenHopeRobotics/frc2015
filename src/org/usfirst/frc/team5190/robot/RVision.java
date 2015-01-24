package org.usfirst.frc.team5190.robot;


import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;


public class RVision {
	
	public int cameraSession;
	public Image frame;
	
	
	/*
	 * @param Initialize Robot Camera Method
	 */
	public void initRobot(){
		
		// Capture Image Type
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		// Enable Camera
		cameraSession = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(cameraSession);
			
	}
	


}
