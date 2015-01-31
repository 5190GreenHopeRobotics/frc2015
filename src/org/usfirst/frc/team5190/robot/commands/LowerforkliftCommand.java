package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @deprecated
 */
public class LowerforkliftCommand extends Command {

	public LowerforkliftCommand() {
		// needs fork lift
		requires(Robot.forkLiftSubsystem);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		Robot.forkLiftSubsystem.stopraiseGrabber();
	}

	@Override
	protected void interrupted() {
		Robot.forkLiftSubsystem.lowerGrabber();
	}
}