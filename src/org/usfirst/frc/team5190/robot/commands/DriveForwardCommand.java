package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * drive forward 100 inches
 */
public class DriveForwardCommand extends Command {

	/**
	 * 
	 */
	public DriveForwardCommand() {
		// needs drive train
		requires(Robot.driveTrainSubsystem);
		Robot.driveTrainSubsystem.setPower(0.1);
		// ini the ultrasonics
	}

	@Override
	protected void initialize() {
		Robot.driveTrainSubsystem.resetEncoder();
		Robot.driveTrainSubsystem.PIDEnable(true);
		Robot.driveTrainSubsystem.driveToPoint(100);
	}

	/**
	 * drive at full speed forward until is 11 inches from an object
	 */
	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.stopAll();
		Robot.driveTrainSubsystem.PIDEnable(false);

	}

	@Override
	protected void interrupted() {
		end();
	}
}
