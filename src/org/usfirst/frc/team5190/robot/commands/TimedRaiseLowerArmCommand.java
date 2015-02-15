package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimedRaiseLowerArmCommand extends Command {

	private boolean raise;

	public TimedRaiseLowerArmCommand(double time, boolean raise) {
		requires(Robot.armSubsystem);
		setTimeout(time);
		this.raise = raise;
	}

	@Override
	protected void initialize() {
		if (raise) {
			Robot.armSubsystem.raiseArm();
		} else {
			Robot.armSubsystem.lowerArm();
		}
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
		Robot.armSubsystem.stopArm();
	}

	@Override
	protected void interrupted() {
		Robot.armSubsystem.stopArm();
	}
}
