package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.commands.ArmSetAngleCommand;
import org.usfirst.frc.team5190.robot.commands.CherryPickCommandGroup;
import org.usfirst.frc.team5190.robot.commands.OneToteCommandGroup;
import org.usfirst.frc.team5190.robot.oi.DisplayableOI;
import org.usfirst.frc.team5190.robot.oi.GamepadOI;
import org.usfirst.frc.team5190.robot.oi.OI;
import org.usfirst.frc.team5190.robot.oi.ScaleInputsOI;
import org.usfirst.frc.team5190.robot.oi.SetPowerCurvesOI;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.LifecycleSubsystemManager;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private Scheduler scheduler;
	private SendableChooser autonomousChooser;
	private Command autonomousCommand;

	/**
	 * The operator interface
	 */
	public static OI oi;

	public Robot() {
		// Initialize OI
		OI joystickOI = new GamepadOI();
		SetPowerCurvesOI powerCurvesOI = new SetPowerCurvesOI(joystickOI);
		ScaleInputsOI scaledInputsOI = new ScaleInputsOI(0.8, powerCurvesOI);
		scaledInputsOI.setCherryPickerScalingValue(0.5);
		scaledInputsOI.setPawlScalingValue(0.3);
		scaledInputsOI.setArmScalingValue(0.5);
		scaledInputsOI.setForwardReverseScalingValue(0.7);
		scaledInputsOI.setLeftRightScalingValue(0.7);
		DisplayableOI displayableOI = new DisplayableOI(scaledInputsOI);
		oi = displayableOI;

		autonomousChooser = new SendableChooser();
		autonomousChooser.addDefault("One Tote", new OneToteCommandGroup());
		autonomousChooser
				.addObject("Cherry Pick", new CherryPickCommandGroup());

		scheduler = Scheduler.getInstance();

		// setup displayables
		SmartDashBoardDisplayer displayer = SmartDashBoardDisplayer
				.getInstance();
		displayer.addDisplayable(DriveTrainSubsystem.getInstance());
		displayer.addDisplayable(ArmSubsystem.getInstance());
		displayer.addDisplayable(PawlSubsystem.getInstance());
		displayer.addDisplayable(CherryPickerSubsystem.getInstance());
		displayer.addDisplayable(displayableOI);
		displayer.addDisplayable(NavigationSubsystem.getInstance());

		// add scheduler, subsystems to dashboard
		SmartDashboard.putData(scheduler);
		SmartDashboard.putData("Autonomous Sequence", autonomousChooser);
		SmartDashboard.putData(DriveTrainSubsystem.getInstance());
		SmartDashboard.putData(ArmSubsystem.getInstance());
		SmartDashboard.putData(PawlSubsystem.getInstance());
		SmartDashboard.putData(CherryPickerSubsystem.getInstance());
		SmartDashboard.putData(NavigationSubsystem.getInstance());

		// testing
		SmartDashboard.putData(new ArmSetAngleCommand(100));
	}

	@Override
	public void robotInit() {
		SmartDashBoardDisplayer.getInstance().start();
	}

	@Override
	public void disabledPeriodic() {
		scheduler.run();
	}

	@Override
	public void autonomousInit() {
		LifecycleSubsystemManager.getInstance().autonomousInit();
		autonomousCommand = (Command) autonomousChooser.getSelected();
		autonomousCommand.start();
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
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
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
