package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDarmexperimentCommand extends Command {

	public PIDarmexperimentCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.PIDExample);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Move the arm up a little, call this command with a held down button
		if (Robot.PIDExample.getEncoderangle() < 80) {
			Robot.PIDExample
					.setSetpoint(Robot.PIDExample.getEncoderangle() + 1);
		}
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
