package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command requires the armSubsystem.
 */
public class RaiseArmCommand extends Command {

	public RaiseArmCommand() {
		requires(Robot.armSubsystem);
		// setTimeout(1.0);
	}

	/**
	 * This starts raising the arm. The arm only raises if the current degrees
	 * is less than 80. The encoder will reset if the direction has just changed
	 */
	protected void initialize() {
		if (Robot.armSubsystem.armLengthEncoder.getDirection() == true) {
			Robot.armSubsystem.armLengthEncoder.reset();
		}
		Robot.armSubsystem.currentdegrees = (Robot.armSubsystem.armLengthEncoder
				.getDistance() / Robot.armSubsystem.shaftcircumference * 360);
		if (Robot.armSubsystem.currentdegrees < 80) {
			Robot.armSubsystem.raiseArm();
		}
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {
	}

	/**
	 * This is returned when the set time is up.
	 */
	protected boolean isFinished() {
		return true;
	}

	/**
	 * This stops the arm from rising when the time ends. WILL add encoder reset
	 * later.
	 */
	protected void end() {
		Robot.armSubsystem.stopArmAngleChange();
	}

	/**
	 * This stops the arm from rising when the code is interrupted.
	 */
	protected void interrupted() {
		Robot.armSubsystem.stopArmAngleChange();

	}
}
// Pressed down limit switch will return false