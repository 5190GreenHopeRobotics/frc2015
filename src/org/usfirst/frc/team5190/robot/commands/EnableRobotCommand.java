package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * enable the drive train
 * 
 * @author sdai
 *
 */
public class EnableRobotCommand extends Command {

	@Override
	protected void initialize() {
	}

	/**
	 * disable the drive train
	 */
	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.resume();
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
		//Robot.driveTrainSubsystem.halt();
	}

}
