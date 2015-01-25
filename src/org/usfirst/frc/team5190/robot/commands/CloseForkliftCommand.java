package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * close the fork lift
 */
public class CloseForkliftCommand extends Command {

	public CloseForkliftCommand() {
		// needs forklift
		requires(Robot.forkLiftSubsystem);

	}

	/**
	 * close the fork
	 */
	@Override
	protected void initialize() {
		Robot.forkLiftSubsystem.closeGrabber();
	}

	protected void execute() {
	}

	/**
	 * return true
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}

	/**
	 * stops the fork lift
	 */
	@Override
	protected void end() {
		Robot.forkLiftSubsystem.stopGrabber();
	}

	@Override
	protected void interrupted() {
		Robot.forkLiftSubsystem.stopGrabber();
	}
}
