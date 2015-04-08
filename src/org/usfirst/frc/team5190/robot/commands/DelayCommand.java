package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DelayCommand extends Command {

	/**
	 * @param seconds
	 *            The amount of time to delay in seconds
	 */
	public DelayCommand(double seconds) {
		setTimeout(seconds);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
