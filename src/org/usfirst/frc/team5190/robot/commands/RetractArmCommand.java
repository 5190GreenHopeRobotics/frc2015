package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractArmCommand extends Command {

	public RetractArmCommand() {
		// needs the arm
		requires(Robot.PIDExample);

	}

	/**
	 * This starts the command, and begins to retract the Arm.
	 */
	protected void initialize() {
		// If the direction changes, reset Encoder count. Need to check
		// directions for true/false
		if (Robot.PIDExample.armExtender.getEncoderdirection() == true) {
			Robot.PIDExample.armExtender.resetEncoder();
		}
		// If the limit switches are not pressed down, retract a little bit
		if (Robot.PIDExample.armExtender.limitswitchextended.get() != false
				&& Robot.PIDExample.armExtender.limitswitchretracted.get() != false) {
			Robot.PIDExample.armExtender
					.setSetpoint(Robot.PIDExample.armExtender.getEncoderangle() + 100);
		}
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {

	}

	/**
	 * This is the returned value after the time is finished.
	 */
	protected boolean isFinished() {
		return true;

	}

	/**
	 * This stops the arm when the time is "out"/ended, and it will start to
	 * rerun.
	 */
	protected void end() {
		Robot.PIDExample.armExtender.stopExtension();
	}

	/**
	 * This stops the arm from retracting when the code is interrupted.
	 */
	protected void interrupted() {
		Robot.PIDExample.armExtender.stopExtension();

	}
}
