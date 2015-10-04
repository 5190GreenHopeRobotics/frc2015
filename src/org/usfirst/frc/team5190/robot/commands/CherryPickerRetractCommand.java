package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CherryPickerRetractCommand extends Command {
	private CherryPickerSubsystem cherryPickerSubsystem = CherryPickerSubsystem
			.getInstance();

	public CherryPickerRetractCommand() {
		requires(cherryPickerSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		cherryPickerSubsystem.operate(-0.2);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return cherryPickerSubsystem.reachedMinLimit();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		cherryPickerSubsystem.stopCherryPicker();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		cherryPickerSubsystem.stopCherryPicker();
	}
}