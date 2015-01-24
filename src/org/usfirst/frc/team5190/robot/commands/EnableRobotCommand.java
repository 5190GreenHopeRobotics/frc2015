package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnableRobotCommand extends Command {
	private final double FULL_SPEED = 1.0;
	private final double FULL_STOP = 0.0;

	@Override
	protected void initialize() {
		requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
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
