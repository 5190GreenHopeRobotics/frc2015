package org.usfirst.frc.team5190.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;

public class ExampleCameraClass {

	CameraServer server;
	int session;
	Image frame;

	public void cameraInit() {
		// server
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);

	}

	public void Cameraforteleoperations() {
		NIVision.IMAQdxStartAcquisition(session);
		NIVision.IMAQdxGrab(session, frame, 1);
		NIVision.imaqColorThreshold(frame, frame, 0, ColorMode.HSL, new Range(
				0, 255), new Range(0, 255), new Range(128, 255));
		CameraServer.getInstance().setImage(frame);
		NIVision.IMAQdxStopAcquisition(session);
	}

}
