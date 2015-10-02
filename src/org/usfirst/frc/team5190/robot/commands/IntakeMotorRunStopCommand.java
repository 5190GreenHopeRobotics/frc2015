package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeMotorRunStopCommand extends Command {

	private IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
	private double direction;

	public IntakeMotorRunStopCommand(double direction) {
		requires(intakeSubsystem);
		this.direction = direction;
	}

	@Override
	protected void initialize() {
		intakeSubsystem.runIntake(direction);
	}

	@Override
	protected void execute() {
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
	}

}
