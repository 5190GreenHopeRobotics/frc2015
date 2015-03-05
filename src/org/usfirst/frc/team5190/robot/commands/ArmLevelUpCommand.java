package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmLevelUpCommand extends Command {

	private final ArmSubsystem armsubsystem = ArmSubsystem.getInstance();
	private boolean waitToFinish;
	private double nextlevel;

	public ArmLevelUpCommand(boolean waitToFinish) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.waitToFinish = waitToFinish;
		requires(armsubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		nextlevel = armsubsystem.levelup();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (waitToFinish) {
			double toprange = nextlevel + 4.45;
			double bottomrange = nextlevel - 4.45;
			return armsubsystem.getAngle() > bottomrange
					&& armsubsystem.getAngle() < toprange;
		}
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
