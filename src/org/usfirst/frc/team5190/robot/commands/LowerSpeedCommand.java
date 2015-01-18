package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.OI;
import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LowerSpeedCommand extends Command {
	private final double FULL_STOP = 0.0;
	private final double HALF_SPEED = .5;
	private final double FULL_SPEED = 1.0;
	OI oi = new OI();
	/**
	 * @return Sets robot speed to half while button 
	 */
	public LowerSpeedCommand() {
		requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void end() {
		Robot.driveTrainSubsystem.drive(FULL_SPEED);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected void initialize() {
		Robot.driveTrainSubsystem.drive(HALF_SPEED);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.drive(FULL_STOP);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
