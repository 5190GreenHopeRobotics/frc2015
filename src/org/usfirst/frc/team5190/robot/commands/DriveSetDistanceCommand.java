package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSetDistanceCommand extends Command {

	private double distance;
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	// requires the drive train Subsystem .
	public DriveSetDistanceCommand(double distance) {
		super("DriveSetDistanceCommand");
		this.distance = distance;
		requires(driveTrainSubsystem);
		setTimeout(3);
	}

	// Drives to the set distance, which is added to the value the encoder is
	// already there.
	protected void initialize() {
		driveTrainSubsystem.setPower(0.3);
		// driveSetDistance = driveTrainSubsystem.driveSetDistance();
		// driveSetDistance.start(distance);
		driveTrainSubsystem.pidDrive(5000);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrainSubsystem.setPower(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		driveTrainSubsystem.setPower(0);
	}
}
