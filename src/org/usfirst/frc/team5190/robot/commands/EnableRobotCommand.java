package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnableRobotCommand extends Command{
	private final double FULL_SPEED = 1.0;
	private final double FULL_STOP = 0.0;
	@Override
	protected void initialize() {
		requires(Robot.driveTrainSubsystem);
	}
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.drive(FULL_SPEED);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.drive(FULL_STOP);
	}

}
