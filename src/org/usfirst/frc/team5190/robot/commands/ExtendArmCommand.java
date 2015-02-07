package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendArmCommand extends Command {
	/**
	 * This requires the armSubsystem and sets the operation segment time as 1
	 * second.
	 */
	public ExtendArmCommand() {
		requires(Robot.armSubsystem);

	}

	/**
	 * This part starts the actual process of the extending of the arm.
	 */
	protected void initialize() {
		// if (Robot.armSubsystem.Extender.getencoderdistance() <
		// Robot.armSubsystem.Extender.maxextension
		// && Robot.armSubsystem.Extender.getextendlimitswitch() == true) {
		// Robot.armSubsystem.Extender.extendarm();
		// }

	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {
	}

	/**
	 * Returns it when the amount of time is finished.
	 */
	protected boolean isFinished() {
		return true;
	}

	/**
	 * Stops the Arm when the Time is out
	 */
	protected void end() {
		Robot.PIDExample.armExtender.stopExtension();
	}

	/**
	 * Stops the Arm when the code is interrupted.
	 */
	protected void interrupted() {
		Robot.PIDExample.armExtender.stopExtension();
	}
}
