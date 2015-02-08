package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSetDistanceCommand extends Command {

	// requires the drive train Subsystem .
	public DriveSetDistanceCommand() {
		requires(Robot.driveTrainSubsystem);
	}

	// Drives to the set distance, which is added to the value the encoder is
	// already there.
	protected void initialize() {
		Robot.driveTrainSubsystem.startDriveSetDistance(36);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		Robot.driveTrainSubsystem.drivenDistance();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSubsystem.resetEncoder();
		Robot.driveTrainSubsystem.endDriveSetDistance();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.driveTrainSubsystem.resetEncoder();
		Robot.driveTrainSubsystem.halt();

	}
}
