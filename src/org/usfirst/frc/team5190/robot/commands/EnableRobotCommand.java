package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnableRobotCommand extends Command {

	@Override
	protected void initialize() {
	}

	/**
	 * disable the drive train
	 */
	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.setDisable(false);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.setDisable(false);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.setDisable(true);
	}

}
