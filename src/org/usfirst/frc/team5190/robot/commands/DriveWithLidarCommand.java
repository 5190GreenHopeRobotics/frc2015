package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithLidarCommand extends Command {

	public DriveWithLidarCommand() {
		requires(Robot.driveWithLidarSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveWithLidarSubsystem.initializeDriveTrain();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.driveWithLidarSubsystem.runDriveTrain();
		if (Robot.driveWithLidarSubsystem.getDistanceFromLidar() <= 9) {

		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
