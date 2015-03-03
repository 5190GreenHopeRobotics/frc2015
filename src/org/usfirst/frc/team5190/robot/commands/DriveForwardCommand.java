package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * drive forward 100 inches
 */
public class DriveForwardCommand extends Command {

	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	/**
	 * 
	 */
	public DriveForwardCommand() {
		super("DriveForwardCommand");
		requires(driveTrainSubsystem);
		driveTrainSubsystem.setPower(0.1);
		// ini the ultrasonics
	}

	@Override
	protected void initialize() {
		// IndependentSensors.getGyro().reset();
		driveTrainSubsystem.resetEncoder();
		// Robot.driveTrainSubsystem.PIDEnable(true);
		// Robot.driveTrainSubsystem.driveToPoint(100);

	}

	/**
	 * drive at full speed forward until is 11 inches from an object
	 */
	@Override
	protected void execute() {
		driveTrainSubsystem.drive(0.5);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		driveTrainSubsystem.drive(0);

	}

	@Override
	protected void interrupted() {
		end();
	}
}
