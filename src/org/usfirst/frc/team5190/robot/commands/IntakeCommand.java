package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {

	private IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
	private double direction;

	public IntakeCommand(double power) {
		requires(intakeSubsystem);
		this.direction = power;
	}

	@Override
	protected void initialize() {
//		if (direction > 0) {
//			intakeSubsystem.firstButtonPressed();
//		} else {
//			intakeSubsystem.secondButtonPressed();
//		}
//		if (!intakeSubsystem.bothPressed()) {
			intakeSubsystem.runIntake(direction);
//		} else {
//			intakeSubsystem.stopIntake();
//			intakeSubsystem.reset();
//		}
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
