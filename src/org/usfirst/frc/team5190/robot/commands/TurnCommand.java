package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * turn for 3 second
 */
public class TurnCommand extends Command {

	private Direction mDir = Direction.LEFT;

	public TurnCommand() {
		setTimeout(3);
	}

	@Override
	protected void initialize() {
	}

	/**
	 * set direction to turn, via Direction enum
	 * 
	 * @param dir
	 *            the direction, RIGHT, LEFT
	 */
	public void setDirection(Direction dir) {
		mDir = dir;
	}

	/**
	 * turn right/lift at rate of 0.5
	 */
	@Override
	protected void execute() {
		if (mDir == Direction.LEFT) {
			Robot.driveTrainSubsystem.turn(-0.5);
		} else if (mDir == Direction.RIGHT) {
			Robot.driveTrainSubsystem.turn(0.5);
		}
	}

	/**
	 * Whether timed out
	 */
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	/**
	 * stop the drive train
	 */
	@Override
	protected void end() {
		Robot.driveTrainSubsystem.drive(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
