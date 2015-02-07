package org.usfirst.frc.team5190.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

public class RVision {

	public int cameraSession;
	CameraServer server;
	public Image frame;

	/*
	 * Initialize Robot Camera
	 */
	public void initRobot() {

		// Capture Image Type
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		// Enable Camera
		cameraSession = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(cameraSession);

	}

	/*
	 * Camera Method for Teleop
	 */
	public void telopCamera() {

		NIVision.IMAQdxStartAcquisition(cameraSession);

		// Grab Image
		NIVision.IMAQdxGrab(cameraSession, frame, 1);
		NIVision.imaqColorThreshold(frame, frame, 0, ColorMode.HSL, new Range(
				0, 255), new Range(0, 255), new Range(128, 255));
		// Set Instance of Camera
		CameraServer.getInstance().setImage(frame);
		NIVision.IMAQdxStopAcquisition(cameraSession);

	}

	/*
	 * Auto Camera Method
	 */
	public void autoCamera() {

		// Create Instance
		NIVision.IMAQdxStartAcquisition(cameraSession);

		NIVision.IMAQdxGrab(cameraSession, frame, 1);
		NIVision.imaqColorThreshold(frame, frame, 1, NIVision.ColorMode.RGB,
				new Range(250, 255), new Range(250, 255), new Range(250, 255));
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 110);
		NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE,
				ShapeMode.SHAPE_RECT, 0.0f);
		Timer.delay(0.005);
		NIVision.IMAQdxStopAcquisition(cameraSession);

	}

}
