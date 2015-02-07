package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * disable the drive train
 * 
 * @author alex
 *
 */
public class TerminateRobotCommand extends Command {
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.halt();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.resume();

	}

}
