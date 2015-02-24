package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.Turn;

import edu.wpi.first.wpilibj.command.Command;

/**
 * turn for 3 second
 */
public class TurnCommand extends Command {

	private double degree;
	private Turn turn;

	public TurnCommand(double degree) {
		this.degree = degree;
		requires(Robot.driveTrainSubsystem);
	}

	@Override
	protected void initialize() {
		// Robot.driveTrainSubsystem.setPower(0.3);
		turn = Robot.driveTrainSubsystem.turn();
		turn.start(degree);
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return turn.finishedTurn();
	}

	/**
	 * stop the turn
	 */
	@Override
	protected void end() {
		turn.end();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
