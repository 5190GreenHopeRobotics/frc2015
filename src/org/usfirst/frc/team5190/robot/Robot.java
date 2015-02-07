package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.DriveForwardCommand;
import org.usfirst.frc.team5190.robot.commands.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
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

	// hardware not present
	public static IndependentSensors sensors;
	// hardware not present
	public static ArmSubsystem armSubsystem = null;
	private DriveForwardCommand autonomousCommand;
	private LidarFilter lidar;
	public static NavigationSubsystem navigationSubsystem = null;
	// working code
	public static DriveTrainSubsystem driveTrainSubsystem;
	public static VisionSubsystem vision;
	// Prototype arm
	public static Prototypearm prototype = new Prototypearm();

	/**
	 * the userInterface
	 */
	public static OI oi;
	static {
		oi = new OI();
	}

	public Robot() {
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
