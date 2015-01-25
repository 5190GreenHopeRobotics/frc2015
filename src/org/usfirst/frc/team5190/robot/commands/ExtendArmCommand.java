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
		requires(Robot.armSubsystem);
		setTimeout(1.0);
	}

	/**
	 * This part starts the actual process of the extending of the arm.
	 */
	@Override
	protected void initialize() {
		Robot.armSubsystem.extendArm();
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	@Override
	protected void execute() {
	}

	/**
	 * Returns it when the amount of time is finished.
	 */
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	/**
	 * Stops the Arm when the Time is out
	 */
	@Override
	protected void end() {
		Robot.armSubsystem.stopArmLengthChange();
	}

	/**
	 * Stops the Arm when the code is interrupted.
	 */
	@Override
	protected void interrupted() {
		Robot.armSubsystem.stopArmLengthChange();

	}
}
