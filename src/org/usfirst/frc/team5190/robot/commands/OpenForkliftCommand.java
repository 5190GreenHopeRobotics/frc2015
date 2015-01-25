package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenForkliftCommand extends Command {

	public OpenForkliftCommand() {
		requires(Robot.forkLiftSubsystem);
	}

	/**
	 * opens the grabber in forklift
	 */
	@Override
	protected void initialize() {
		Robot.forkLiftSubsystem.openGrabber();
	}

	/**
	 * do nothing
	 */
	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		// Line below is for if we use an ultrasonic sensor
		// return Robot.robotPawSubsystem.pawUltrasonic.getRangeInches() < 35;
		return true;
	}

	@Override
	protected void end() {
		Robot.forkLiftSubsystem.stopGrabber();
	}

	@Override
	protected void interrupted() {
		Robot.forkLiftSubsystem.stopGrabber();
	}
}
