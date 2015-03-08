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

	private static boolean flag;

	public CherryPickerJoystickCommand() {
		requires(cherryPickerSubsystem);
	}

	@Override
	protected void initialize() {

		cherryPickerSubsystem.resetCounter();
		if (cherryPickerSubsystem.isSwitchPressed()
				&& Robot.oi.getCherryPickerAxis() < 0) {
			flag = false;
		} else {
			flag = true;
		}
	}

	@Override
	protected void execute() {

		if (flag = false) {
			cherryPickerSubsystem.operate(Robot.oi.getCherryPickerAxis());
		} else {
			cherryPickerSubsystem.retract();

		}
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
