package org.usfirst.frc.team5190.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision {

	int session;
	Image frame;

	public void visionInit() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		session = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
	}

	public void visionTeleop() {
		NIVision.IMAQdxStartAcquisition(session);

		/**
		 * grab an image, draw the circle, and provide it for the camera server
		 * which will in turn send it to the dashboard.
		 */
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
		NIVision.IMAQdxGrab(session, frame, 1);
		// NIVision.imaqDrawShapeOnImage(frame, frame, rect,
		// DrawMode.DRAW_VALUE,
		// ShapeMode.SHAPE_OVAL, 0.0f);

		NIVision.imaqDrawLineOnImage(frame, frame,
				NIVision.DrawMode.DRAW_VALUE, new Point(0, 0), new Point(200,
						200), 10);
		NIVision.imaqDrawLineOnImage(frame, frame,
				NIVision.DrawMode.DRAW_VALUE, new Point(0, 200), new Point(200,
						250), 10);
		// NIVision.imaqDrawLineOnImage(frame, frame,
		// NIVision.DrawMode.DRAW_VALUE, new Point(), new Point(), 10);
		CameraServer.getInstance().setImage(frame);
		NIVision.IMAQdxStopAcquisition(session);
	}
}
