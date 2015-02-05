package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CameraServoSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.PIDarmexperimentPIDSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.VisionSubsystem;
import org.usfirst.frc.team5190.smartDashBoard.SmartDashBoardDisplayer;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	boolean RobotIsEnabled = true;
	// camera
	public static CameraServoSubsystem cameraServoSubsystem = new CameraServoSubsystem();

	// hardware not present
	public static IndependentSensors sensors = new IndependentSensors();
	// hardware not present
	public static ArmSubsystem armSubsystem = null;
	private DriveForwardCommand autonomousCommand = new DriveForwardCommand();
	public static NavigationSubsystem navigationSubsystem = null;
	// working code
	public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
	// Experiment, don't touch plz
	public static PIDarmexperimentPIDSubsystem PIDExample = null;
	public static VisionSubsystem visioin = null;
	// Camera test
	public int cameraSession;
	public Image cameraFrame;
	public CameraServer server;

	/**
	 * 
	 * @author rd124p7 This class controls the camera, and output the video onto
	 *         the driver station
	 */
	protected class Camera {

		// // Define Some Variables
		// public int cameraSession;
		// public Image cameraFrame;
		// public CameraServer server;
		//
		// Camera() {
		// server = CameraServer.getInstance();
		// server.setQuality(50);
		// // the camera name (ex "cam0") can be found through the roborio
		// // web
		// // // interface
		// server.startAutomaticCapture("cam0");
		// cameraInit();
		// cameraControl();
		// }
		//
		// /**
		// * Initialize the Camera
		// */
		// public void cameraInit() {
		//
		// cameraFrame = NIVision.imaqCreateImage(
		// NIVision.ImageType.IMAGE_RGB, 0);
		//
		// cameraSession = NIVision
		// .IMAQdxOpenCamera(
		// "cam0",
		// NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		//
		// NIVision.IMAQdxConfigureGrab(cameraSession);
		//
		// }
		//
		// public void cameraControl() {
		//
		// NIVision.IMAQdxStartAcquisition(cameraSession);
		//
		// NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
		//
		// while (RobotIsEnabled) {
		//
		// NIVision.IMAQdxGrab(cameraSession, cameraFrame, 1);
		//
		// NIVision.imaqDrawShapeOnImage(cameraFrame, cameraFrame, rect,
		// DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
		//
		// CameraServer.getInstance().setImage(cameraFrame);
		//
		// }
		//
		// NIVision.IMAQdxStopAcquisition(cameraSession);
		//
		// }

	}

	/**
	 * the userInterface
	 */
	public static OI oi;
	static {
		oi = new OI();
	}

	{
		// camera = new Camera();
		SmartDashBoardDisplayer.getInstance().submit(driveTrainSubsystem);
		SmartDashBoardDisplayer.getInstance().submit(sensors);
	}

	// public Camera camera;

	/**
	 * Init the Camera
	 * 
	 */
	public void robotInit() {
		cameraFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		cameraSession = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(cameraSession);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		new PutSmartDashBoardCommand().start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		DriveWithArcadeCommand controledDrive = new DriveWithArcadeCommand();
		controledDrive.start();
		new PutSmartDashBoardCommand().start();
		// new CameraMovementCommand().start();

	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		NIVision.IMAQdxStartAcquisition(cameraSession);
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

		/**
		 * grab an image, draw the circle, and provide it for the camera server
		 * which will in turn send it to the dashboard.
		 */

		NIVision.IMAQdxGrab(cameraSession, cameraFrame, 1);
		NIVision.imaqDrawShapeOnImage(cameraFrame, cameraFrame, rect,
				DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
		// ======
		// NIVision.imaqColorThreshold(frame, frame, 50, ColorMode.HSV, hue,
		// saturation, value);
		// ======
		CameraServer.getInstance().setImage(cameraFrame);

		/** robot code here! **/
		Timer.delay(0.005);
		NIVision.IMAQdxStopAcquisition(cameraSession);
		Scheduler.getInstance().run();

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
