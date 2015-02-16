package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CherryPickerOperateCommand extends Command {
	private Joystick shootStick;
	private Joystick gamepad;

	public CherryPickerOperateCommand() {
		shootStick = Robot.oi.getShootStick();
		gamepad = Robot.oi.getGamepad();
		requires(Robot.cherryPickerSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// if (shootStick.getRawButton(1)) { // when trigger toggled
		// Robot.cherryPickerSubsystem.operateWithGamepad(gamepad);
		// }
		Joystick joystick = Robot.oi.getDriveStick();
		double retractValue = joystick.getRawAxis(3);
		double extendValue = joystick.getRawAxis(2);
		if (retractValue > 0.0) {
			Robot.cherryPickerSubsystem.operate(-retractValue);
		} else {
			Robot.cherryPickerSubsystem.operate(extendValue);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
