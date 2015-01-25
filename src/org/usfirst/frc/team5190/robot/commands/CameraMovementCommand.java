package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * the servo which camera mounts on
 */
public class CameraMovementCommand extends Command {

	public CameraMovementCommand() {
		// need servo
		requires(Robot.cameraServoSubsystem);
		Robot.cameraServoSubsystem.setZero();
	}

	/**
	 * scan the field
	 */
	@Override
	protected void initialize() {

		Robot.cameraServoSubsystem.scanField();

	}

	/**
	 * do nothing
	 */
	@Override
	protected void execute() {
	}

	/**
	 * run infinitly
	 */
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
