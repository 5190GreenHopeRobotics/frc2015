package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CherryPickerOperateCommand extends Command {
	private boolean needToRetract;
	private boolean needToStop;
	private Joystick shootStick;

	public CherryPickerOperateCommand() {
		shootStick = Robot.oi.getShootStick();
		requires(Robot.cherryPickerSubsystem);
		requires(Robot.armSubsystem);
		needToRetract = false;
		needToStop = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (shootStick.getRawButton(1)) { // when trigger toggled
			Robot.cherryPickerSubsystem.operateWithJoystick(shootStick);
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
