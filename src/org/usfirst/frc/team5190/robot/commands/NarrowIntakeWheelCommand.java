package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NarrowIntakeWheelCommand extends Command {

	public NarrowIntakeWheelCommand() {
		requires(IntakeSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		IntakeSubsystem.getInstance().narrowIntakeWheel();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
