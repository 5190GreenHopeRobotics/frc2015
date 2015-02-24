package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.DriveSetDistance;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSetDistanceCommand extends Command {

	private double distance;
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private DriveSetDistance driveSetDistance;

	// requires the drive train Subsystem .
	public DriveSetDistanceCommand(double distance) {
		this.distance = distance;
		requires(driveTrainSubsystem);
	}

	// Drives to the set distance, which is added to the value the encoder is
	// already there.
	protected void initialize() {
		driveTrainSubsystem.setPower(0.4);
		driveSetDistance = driveTrainSubsystem.driveSetDistance();
		driveSetDistance.start(distance);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return driveSetDistance.drivenDistance();
	}

	// Called once after isFinished returns true
	protected void end() {
		driveSetDistance.end();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		driveSetDistance.end();
	}
}
