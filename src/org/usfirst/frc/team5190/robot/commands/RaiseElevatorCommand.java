package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseElevatorCommand extends Command {

	public RaiseElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.elevatorSubsystem.raiseElevator();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.elevatorSubsystem.stopElevator();
	}

	@Override
	protected void interrupted() {
		Robot.elevatorSubsystem.stopElevator();
	}
}
