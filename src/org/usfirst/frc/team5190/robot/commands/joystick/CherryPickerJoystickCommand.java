package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CherryPickerJoystickCommand extends Command {
	private CherryPickerSubsystem cherryPickerSubsystem = CherryPickerSubsystem
			.getInstance();

	public CherryPickerJoystickCommand() {
		requires(cherryPickerSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		cherryPickerSubsystem.operate(Robot.oi.getCherryPickerAxis());

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}