package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * drive with arcade drive
 */
public class DriveWithArcadeCommand extends Command {

	public DriveWithArcadeCommand() {
		// needs drive train
		requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.arcadeJoystickDrive(Robot.oi.getDriveStick());
		// Robot.driveTrainSubsystem.setPower(Robot.oi.getSpeed());
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
		end();
	}
}
