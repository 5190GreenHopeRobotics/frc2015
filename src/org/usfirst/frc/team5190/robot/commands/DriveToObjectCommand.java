package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToObjectCommand extends Command {

	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private NavigationSubsystem navigationSubsystem = NavigationSubsystem
			.getInstance();
	private PIDController pidController;
	private double centimetersToTarget;

	public DriveToObjectCommand() {
		requires(navigationSubsystem);
		requires(driveTrainSubsystem);

		PIDSource pidSource = navigationSubsystem
				.createLidarDistancePIDSource();
		PIDOutput pidOutput = driveTrainSubsystem
				.createDriveStraightPIDOutput();
		pidController = new PIDController(-.01, 0, 0, pidSource, pidOutput,
				0.01);
		pidController.setAbsoluteTolerance(2);
		pidController.setOutputRange(-0.3, 0.3);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		centimetersToTarget = navigationSubsystem.getLidarDistanceFromObject();
		if (centimetersToTarget > 63) {
			pidController.setSetpoint(63);
		} else {
			pidController.setSetpoint(centimetersToTarget);
		}
		pidController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return pidController.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		pidController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
