package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithLidarCommand;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CameraServoSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveWithLidarSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.PIDarmexperimentPIDSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.Prototypearm;
import org.usfirst.frc.team5190.robot.subsystems.VisionSubsystem;
import org.usfirst.frc.team5190.sensorFilter.Lidar;
import org.usfirst.frc.team5190.sensorFilter.LidarFilter;
import org.usfirst.frc.team5190.smartDashBoard.SmartDashBoardDisplayer;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	public static CameraServoSubsystem cameraServoSubsystem;

	// hardware not present
	public static IndependentSensors sensors;
	// hardware not present
	public static ArmSubsystem armSubsystem = null;
	private DriveForwardCommand autonomousCommand;
	private LidarFilter lidar;
	public static NavigationSubsystem navigationSubsystem = null;
	// working code
	public static DriveTrainSubsystem driveTrainSubsystem;
	// Experiment, don't touch plz
	public static PIDarmexperimentPIDSubsystem PIDExample = null;
	public static VisionSubsystem vision;
	// Prototype arm
	public static Prototypearm prototypearm = new Prototypearm();
	public static DriveWithLidarSubsystem driveWithLidarSubsystem = null;

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

	// public Camera camera;

	/**
	 * Init the Camera
	 * 
	 */

	public Robot() {
		cameraServoSubsystem = new CameraServoSubsystem();
		driveWithLidarSubsystem = new DriveWithLidarSubsystem();
		sensors = new IndependentSensors();
		driveTrainSubsystem = new DriveTrainSubsystem();
		lidar = new LidarFilter(new Lidar(Port.kMXP));
		autonomousCommand = new DriveForwardCommand();
		SmartDashBoardDisplayer.getInstance().submit(driveTrainSubsystem);
		SmartDashBoardDisplayer.getInstance().submit(sensors);
		SmartDashBoardDisplayer.getInstance().submit(lidar);
	}

	public void robotInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		new DriveWithLidarCommand().start();
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
	 *
	 *
	 * grab an image, draw the circle, and provide it for the camera server
	 * which will in turn send it to the dashboard.
	 */

	public void teleopPeriodic() {

		// vision.run();
		Scheduler.getInstance().run();

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
