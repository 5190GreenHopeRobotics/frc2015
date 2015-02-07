package org.usfirst.frc.team5190.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VisionSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	CameraServer server;
	int session;
	Image frame;

	public VisionSubsystem() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
	}

	public void run() {
		NIVision.IMAQdxStartAcquisition(session);
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

		/**
		 * grab an image, draw the circle, and provide it for the camera server
		 * which will in turn send it to the dashboard.
		 */

		NIVision.IMAQdxGrab(session, frame, 1);
		// NIVision.imaqDrawShapeOnImage(frame, frame, rect,
		// DrawMode.DRAW_VALUE,
		// ShapeMode.SHAPE_OVAL, 0.0f);
		NIVision.imaqColorThreshold(frame, frame, 1, NIVision.ColorMode.RGB,
				new Range(0, 255), new Range(0, 255), new Range(128, 255));
		NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE,
				ShapeMode.SHAPE_RECT, 0.0f);
		// ======
		// NIVision.imaqColorThreshold(frame, frame, 50, ColorMode.HSV, hue,
		// saturation, value);
		// ======
		CameraServer.getInstance().setImage(frame);

		NIVision.IMAQdxStopAcquisition(session);
	}

	protected void initDefaultCommand() {

	}
}
