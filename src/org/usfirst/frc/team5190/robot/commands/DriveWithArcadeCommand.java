package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
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
		Joystick driveStick = Robot.oi.getDriveStick();
		double moveValue = driveStick.getRawAxis(1);
		double rotateValue = driveStick.getRawAxis(0);
		// square the inputs (while preserving the sign) to increase fine
		// control while permitting full power
		if (moveValue >= 0.0) {
			moveValue = (moveValue * moveValue);
		} else {
			moveValue = -(moveValue * moveValue);
		}
		if (rotateValue >= 0.0) {
			rotateValue = (rotateValue * rotateValue);
		} else {
			rotateValue = -(rotateValue * rotateValue);
		}
		Robot.driveTrainSubsystem.arcadeJoystickDrive(moveValue, rotateValue);
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
