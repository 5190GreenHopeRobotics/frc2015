package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * drive with arcade drive
 */
public class DriveWithArcadeCommand extends Command {

	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	public DriveWithArcadeCommand() {
		super("DriveWithArcadeCommand");
		requires(driveTrainSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double moveValue = Robot.joystickOI.getForwardReverseAxis();
		double rotateValue = Robot.joystickOI.getLeftRightAxis();
		driveTrainSubsystem.arcadeJoystickDrive(moveValue, rotateValue);
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
