package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.DriveSetDistance;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToObjectCommand extends Command {

	private DriveSetDistance d;
	private double distanceBuffer;
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private PIDController pidController;

	public DriveToObjectCommand() {
		super("DriveToObjectCommand");
		requires(driveTrainSubsystem);
		d = driveTrainSubsystem.driveSetDistance();
		// IndependentSensors.getGyro().reset(); //Temporary comment for testing
		// purposes
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrainSubsystem.setPower(0.5);
		// lidar inoperable at the moment
		// distanceBuffer = IndependentSensors.getLidar().getValue();
		distanceBuffer *= 2.54; // cm to inches
		distanceBuffer -= 2; // estimated buffer distance so the edge doesn't
								// hit the object
		d.start(distanceBuffer);

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return d.drivenDistance();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// IndependentSensors.getGyro().reset();
		d.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
