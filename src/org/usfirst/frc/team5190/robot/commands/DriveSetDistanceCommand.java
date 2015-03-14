package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem.DriveSetDistance;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSetDistanceCommand extends Command {

	private double distance;
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private DriveSetDistance driveSetDistance;

	public DriveSetDistanceCommand(double distance) {
		super("DriveSetDistanceCommand");
		this.distance = distance;
		requires(driveTrainSubsystem);
	}

	@Override
	protected void initialize() {
		driveSetDistance = driveTrainSubsystem.driveSetDistance();
		driveSetDistance.start(distance);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return driveSetDistance.drivenDistance();
	}

	@Override
	protected void end() {
		driveSetDistance.end();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
