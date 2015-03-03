package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.DriveSetDistance;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.Turn;
import org.usfirst.frc.team5190.util.Pair;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToCoordinateCommand extends Command {

	protected Pair<Double, Double> coord;
	protected Pair<Double, Double> xyDifference;
	protected DriveSetDistance d;
	protected Turn t;
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	public DriveToCoordinateCommand(double x, double y) {
		super("DriveToCoordinateCommand");
		requires(driveTrainSubsystem);
		coord = new Pair<Double, Double>(x, y);
		xyDifference = new Pair<Double, Double>();
		d = driveTrainSubsystem.driveSetDistance();
		t = driveTrainSubsystem.turn();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Pair<Double, Double> currentCoord = Robot.robotCoordinate
		// .getCoordinate();
		// double turnDegree;
		// double driveDistance;
		// xyDifference.setFirst(coord.first() - currentCoord.first());
		// xyDifference.setSecond(coord.second() - currentCoord.second());
		// turnDegree = Math.toDegrees(Math.atan2(xyDifference.second(),
		// xyDifference.first()));
		// while (!t.finishedTurn()) {
		// try {
		// Thread.sleep(20);
		// } catch (InterruptedException e) {
		// end();
		// return;
		// }
		// }
		// turnDegree = Math.toRadians(turnDegree);
		// driveDistance = xyDifference.second() / Math.sin(turnDegree);
		// d.start(driveDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return d.drivenDistance();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		d.end();
		t.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
