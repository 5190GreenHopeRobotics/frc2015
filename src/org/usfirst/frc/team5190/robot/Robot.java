package org.usfirst.frc.team5190.robot;


import org.usfirst.frc.team5190.robot.commands.CameraMovementCommand;
import org.usfirst.frc.team5190.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CameraServoSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ForkliftSubsystem;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
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

	private Command autonomousCommand = new DriveForwardCommand();
	public static ArmSubsystem armSubsystem = null; // new ArmSubsystem();
	public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
	public static ForkliftSubsystem forkLiftSubsystem = null; // new
	public static CameraServoSubsystem cameraServoSubsystem = new CameraServoSubsystem();

	protected class Camera {

		// Define Some Variables
		int cameraSession;
		Image cameraFrame;
		CameraServer server;

		Camera() {
			server = CameraServer.getInstance();
			server.setQuality(50);
			// the camera name (ex "cam0") can be found through the roborio web
			// interface
			server.startAutomaticCapture("cam0");
		}

		public void cameraInit() {

			cameraFrame = NIVision.imaqCreateImage(
					NIVision.ImageType.IMAGE_RGB, 0);

			cameraSession = NIVision
					.IMAQdxOpenCamera(
							"cam0",
							NIVision.IMAQdxCameraControlMode.CameraControlModeController);

			NIVision.IMAQdxConfigureGrab(cameraSession);

		}

		public void cameraControl() {

			NIVision.IMAQdxStartAcquisition(cameraSession);

			NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

			while (RobotIsEnabled) {

				NIVision.IMAQdxGrab(cameraSession, cameraFrame, 1);

				NIVision.imaqDrawShapeOnImage(cameraFrame, cameraFrame, rect,
						DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);

				CameraServer.getInstance().setImage(cameraFrame);

			}

			NIVision.IMAQdxStopAcquisition(cameraSession);

		}

	}

	public static OI oi;
	static {
		oi = new OI();
	}
	// Command autonomousCommand;
	{
		camera = new Camera();
	}
	public Camera camera;

	// public robotValues val;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// instantiate the command used for the autonomous period
		
		new CameraMovementCommand().start();
		
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		DriveWithArcadeCommand controledDrive = new DriveWithArcadeCommand();
		controledDrive.start();
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
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
