package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.commands.PutSmartDashBoardCommand;
import org.usfirst.frc.team5190.robot.commands.StackedTotesAutonomousCommandGroup;
import org.usfirst.frc.team5190.robot.oi.DisplayableOI;
import org.usfirst.frc.team5190.robot.oi.FlightStickWithGamePadOI;
import org.usfirst.frc.team5190.robot.oi.OI;
import org.usfirst.frc.team5190.robot.oi.ScaleInputsOI;
import org.usfirst.frc.team5190.robot.oi.SetPowerCurvesOI;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.LifecycleSubsystemManager;
import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

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
	// public static Vision usbCamera;

	private Command autonomousCommand;
	private Scheduler scheduler;

	/**
	 * The operator interface
	 */
	public static OI oi;

	public Robot() {
		// Initialize OI
		OI joystickOI = new FlightStickWithGamePadOI(0, 1);
		SetPowerCurvesOI powerCurvesOI = new SetPowerCurvesOI(joystickOI);
		ScaleInputsOI scaledInputsOI = new ScaleInputsOI(0.8, powerCurvesOI);
		scaledInputsOI.setCherryPickerScalingValue(0.5);
		scaledInputsOI.setPawlScalingValue(0.5);
		scaledInputsOI.setArmScalingValue(0.5);
		scaledInputsOI.setForwardReverseScalingValue(0.5);
		DisplayableOI displayableOI = new DisplayableOI(scaledInputsOI);
		oi = displayableOI;

		autonomousCommand = new StackedTotesAutonomousCommandGroup();
		scheduler = Scheduler.getInstance();

		SmartDashBoardDisplayer displayer = SmartDashBoardDisplayer
				.getInstance();
		displayer.addDisplayable(DriveTrainSubsystem.getInstance());
		displayer.addDisplayable(ArmSubsystem.getInstance());
		displayer.addDisplayable(PawlSubsystem.getInstance());
		displayer.addDisplayable(CherryPickerSubsystem.getInstance());
		displayer.addDisplayable(displayableOI);
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledPeriodic() {
		scheduler.run();
	}

	@Override
	public void autonomousInit() {
		LifecycleSubsystemManager.getInstance().autonomousInit();
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
		scheduler.run();
	}

	@Override
	public void teleopInit() {
		LifecycleSubsystemManager.getInstance().teleopInit();
		if (autonomousCommand != null)
			autonomousCommand.cancel();

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
		scheduler.run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
