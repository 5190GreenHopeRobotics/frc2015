package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.OI;
import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LowerSpeedCommand extends Command {
	private final double FULL_STOP = 0.0;
	private final double HALF_SPEED = .5;
	private final double FULL_SPEED = 1.0;
	OI oi = Robot.oi;

	/**
	 * @return Sets robot speed to half while button
	 */
	public LowerSpeedCommand() {
		requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.setPower(FULL_SPEED);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected void initialize() {
		Robot.driveTrainSubsystem.setPower(HALF_SPEED);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.setPower(FULL_STOP);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
