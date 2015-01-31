package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendArmCommand extends Command {
	/**
	 * This requires the armSubsystem and sets the operation segment time as 1
	 * second.
	 */
	public ExtendArmCommand() {
		requires(Robot.PIDExample);
	}

	/**
	 * This part starts the actual process of the extending of the arm.
	 */
	protected void initialize() {
		// If the direction changes, reset Encoder count. Need to check
		// directions for true/false
		if (Robot.PIDExample.armExtender.getEncoderdirection() == false) {
			Robot.PIDExample.armExtender.resetEncoder();
		}
		// If the limit switches are not pressed down, extend a little
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
	 * Returns it when the amount of time is finished.
	 */
	protected boolean isFinished() {
		return true;
	}

	/**
	 * Stops the Arm when the Time is out
	 */
	protected void end() {
		Robot.PIDExample.armExtender.stopExtension();
	}

	/**
	 * Stops the Arm when the code is interrupted.
	 */
	protected void interrupted() {
		Robot.PIDExample.armExtender.stopExtension();
	}
}
