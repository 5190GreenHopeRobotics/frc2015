package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//Stop robot upon driver pressing kill switch (button 11 on joystick)
public class TerminateRobotCommand extends Command {

	@Override
	protected void initialize() {
		// requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.setDisable(true);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.setDisable(true);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.setDisable(true);

	}

}
