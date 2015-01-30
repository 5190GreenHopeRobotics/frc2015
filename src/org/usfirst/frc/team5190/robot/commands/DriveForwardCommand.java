package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 * drive forward until 11 inches from an object
 */
public class DriveForwardCommand extends Command {
	private Ultrasonic ultraSonicSensor;

	/**
	 * 
	 */
	public DriveForwardCommand() {
		// needs drive train
		requires(Robot.driveTrainSubsystem);
		// ini the ultrasonics
		ultraSonicSensor = new Ultrasonic(RobotMap.ULTRASONIC_PING,
				RobotMap.ULTRASONIC_RECIEVE);
		ultraSonicSensor.setEnabled(true);
	}

	@Override
	protected void initialize() {
	}

	/**
	 * drive at full speed forward until is 11 inches from an object
	 */
	@Override
	protected void execute() {

		if (ultraSonicSensor.getRangeInches() > 11) {
			Robot.driveTrainSubsystem.drive(-0.8);
		} else {
			Robot.driveTrainSubsystem.stopAll();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.stopAll();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
