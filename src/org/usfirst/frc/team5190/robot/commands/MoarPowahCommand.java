package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoarPowahCommand extends Command {

	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private boolean moarPlease;

	public MoarPowahCommand(boolean moarPlease) {
		requires(driveTrainSubsystem);
		this.moarPlease = moarPlease;
	}

	@Override
	protected void initialize() {
		driveTrainSubsystem.moarPowah(moarPlease);
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
