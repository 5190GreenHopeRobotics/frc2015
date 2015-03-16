package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmLevelDownCommand extends Command {

	private final ArmSubsystem armsubsystem = ArmSubsystem.getInstance();
	private double previouslevel;

	public ArmLevelDownCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(armsubsystem);
		// setInterruptible(false);
		setTimeout(0.4);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		previouslevel = armsubsystem.leveldown();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// double toprange = previouslevel + 8;
		// double bottomrange = previouslevel - 8;
		// return armsubsystem.getAngle() < toprange
		// && armsubsystem.getAngle() > bottomrange;
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		// System.out
		// .println("Something Ended The DownLevel Command Button As Well.");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// System.out
		// .println("Something Interrupted The DownLevel Command Button As Well.");
	}
}
