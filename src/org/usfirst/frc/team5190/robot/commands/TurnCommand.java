package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.IndependentSensors;
import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * turn for 3 second
 */
public class TurnCommand extends Command {

	private Direction mDir = Direction.LEFT;
	private Gyro gyro;
	private PIDController pid;

	public TurnCommand() {

	}

	public void enable() {
		pid.enable();
	}

	public void disable() {
		pid.disable();
	}

	@Override
	protected void initialize() {
		// requires(Robot.driveTrainSubsystem);
		Robot.driveTrainSubsystem.setPower(0.3);
		gyro = IndependentSensors.getGyro();
		pid = new PIDController(0.3, 0, 0.1, gyro, Robot.driveTrainSubsystem);
		pid.enable();
		pid.setSetpoint(180);
	}

	public void setDegree(double degree) {
		pid.setSetpoint(degree);
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

	}

	/**
	 * Whether timed out
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}

	/**
	 * stop the drive train
	 */
	@Override
	protected void end() {
		pid.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
