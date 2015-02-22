package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.grid.Grid;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.commands.StackedTotesAutonomousCommandGroup;
import org.usfirst.frc.team5190.robot.commands.TeleopCommandGroup;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveWithLidarSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.LifecycleSubsystemManager;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
import org.usfirst.frc.team5190.smartDashBoard.SmartDashBoardDisplayer;

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

	public static Grid robotCoordinate;
	public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
	public static ArmSubsystem armSubsystem = new ArmSubsystem();
	public static CherryPickerSubsystem cherryPickerSubsystem = new CherryPickerSubsystem();

	public static IndependentSensors sensors = null;
	public static NavigationSubsystem navigationSubsystem = null;
	public static DriveWithLidarSubsystem driveWithLidarSubsystem = null;

	public static Vision USBcamera;

	private Command autonomousCommand;

	/**
	 * the userInterface
	 */
	public static OI oi;
	static {
		// subsystems must be instantiated/initialized before operator interface
		oi = new OI();
		robotCoordinate = new Grid(0.0, 0.0);
	}

	// public Camera camera;

	/**
	 * Init the Camera
	 * 
	 */

	// /// LEFT trigger moves the right side
	// /// Right stick moves the left side

	public Robot() {
		autonomousCommand = new StackedTotesAutonomousCommandGroup();

		// USBcamera = new Vision();
		// USBcamera.visionInit();

		SmartDashBoardDisplayer.getInstance().submit(driveTrainSubsystem);
		SmartDashBoardDisplayer.getInstance().submit(armSubsystem);
		SmartDashBoardDisplayer.getInstance().submit(robotCoordinate);
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		LifecycleSubsystemManager.getInstance().autonomousInit();
		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		new PutSmartDashBoardCommand().start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		LifecycleSubsystemManager.getInstance().teleopInit();
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		new TeleopCommandGroup().start();
		new PutSmartDashBoardCommand().start();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		LifecycleSubsystemManager.getInstance().disable();
	}

	/**
	 * This function is called periodically during operator control
	 *
	 *
	 * grab an image, draw the circle, and provide it for the camera server
	 * which will in turn send it to the dashboard.
	 */

	@Override
	public void teleopPeriodic() {
		// USBcamera.visionTeleop();
		Scheduler.getInstance().run();

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
