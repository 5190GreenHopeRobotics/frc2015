package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.CameraMovementCommand;
import org.usfirst.frc.team5190.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.commands.EncoderTestCommand;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CameraServoSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ForkliftSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.PIDarmexperimentPIDSubsystem;
import org.usfirst.frc.team5190.smartDashBoard.SmartDashBoardDisplayer;

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
	// camera
	public static CameraServoSubsystem cameraServoSubsystem = new CameraServoSubsystem();

	// hardware not present
	public static ElevatorSubsystem elevatorSubsystem = null;
	public static IndependentSensors sensors = new IndependentSensors();
	private Command autonomousCommand = new DriveForwardCommand();
	// hardware not present
	public static ArmSubsystem armSubsystem = null;
	// working code
	public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
	// hardware not present
	public static ForkliftSubsystem forkLiftSubsystem = null;
	// Experiment, don't touch plz
	public static PIDarmexperimentPIDSubsystem PIDExample = null;

	/**
	 * 
	 * @author rd124p7 This class controls the camera, and output the video onto
	 *         the driver station
	 */
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

		/**
		 * Initialize the Camera
		 */
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

	/**
	 * the userInterface
	 */
	public static OI oi;
	static {
		oi = new OI();
	}

	{
		camera = new Camera();
		SmartDashBoardDisplayer.getInstance().submit(driveTrainSubsystem);
		SmartDashBoardDisplayer.getInstance().submit(sensors);
	}
	public Camera camera;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		new PutSmartDashBoardCommand().start();

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

		new EncoderTestCommand().start();
		new CameraMovementCommand().start();

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
