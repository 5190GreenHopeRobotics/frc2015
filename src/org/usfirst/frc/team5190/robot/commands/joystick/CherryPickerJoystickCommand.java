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
	private static boolean flag = false;

	public CherryPickerJoystickCommand() {
		requires(cherryPickerSubsystem);
	}

	@Override
	protected void initialize() {
		if (cherryPickerSubsystem.reachedMinLimit() == true
				&& Robot.joystickOI.getCherryPickerAxis() < 0) {
			flag = true;
		}
		flag = false;
	}

	@Override
	protected void execute() {
		if (flag == false) {
			cherryPickerSubsystem.operate(Robot.oi.getCherryPickerAxis());
		} else {
			cherryPickerSubsystem.retractCherryPicker();
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
