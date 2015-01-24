package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardCommand extends Command {
	private Ultrasonic ultraSonicSensor;

	public DriveForwardCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrainSubsystem);
		ultraSonicSensor = new Ultrasonic(RobotMap.ULTRASONIC_PING,
				RobotMap.ULTRASONIC_RECIEVE);
		ultraSonicSensor.setAutomaticMode(true);
		ultraSonicSensor.setEnabled(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	/**
	 * drive at full speed forward util is 5 inches from an object
	 */
	@Override
	protected void execute() {

		if (ultraSonicSensor.getRangeInches() > 5) {
			Robot.driveTrainSubsystem.drive(-0.5);
		} else {
			Robot.driveTrainSubsystem.stopAll();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSubsystem.stopAll();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
