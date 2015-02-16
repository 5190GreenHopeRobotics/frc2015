package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmTeleopCommand extends Command {

	public ArmTeleopCommand() {
		requires(Robot.armSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.armSubsystem.joystickControl(Robot.oi.getDriveStick());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
