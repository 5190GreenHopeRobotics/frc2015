package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerforkliftCommand extends Command {

	public LowerforkliftCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.forkLiftSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.forkLiftSubsystem.lowerGrabber();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Robot.forkLiftSubsystem.stopraiseGrabber();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// Robot.forkLiftSubsystem.stopraiseGrabber();
		Robot.forkLiftSubsystem.lowerGrabber();
	}
}