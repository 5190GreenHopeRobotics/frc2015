package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.smartDashBoard.SmartDashBoardDisplayer;

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
		SmartDashBoardDisplayer.getNewInstance().display(
				Robot.driveTrainSubsystem);
		Robot.driveTrainSubsystem.arcadeJoystickDrive(Robot.oi.getDriveStick());
		// Robot.driveTrainSubsystem.putData();
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
