package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractArmCommand extends Command {

	public RetractArmCommand() {
		// needs the arm
		// requires(Robot.armSubsystem.Extender);

	}

	/**
	 * This starts the command, and begins to retract the Arm.
	 */
	protected void initialize() {
		// Change the minimum extension when you get a real value
		// if (Robot.armSubsystem.Extender.getencoderdistance() > 2
		// && Robot.armSubsystem.Extender.getretractlimitswitch() == true) {
		// Robot.armSubsystem.Extender.retractarm();
		// }
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {

	}

	/**
	 * This is the returned value after the time is finished.
	 */
	protected boolean isFinished() {
		return true;

	}

	/**
	 * This stops the arm when the time is "out"/ended, and it will start to
	 * rerun.
	 */
	protected void end() {
		Robot.PIDExample.armExtender.stopExtension();
	}

	/**
	 * This stops the arm from retracting when the code is interrupted.
	 */
	protected void interrupted() {
		Robot.PIDExample.armExtender.stopExtension();

	}
}
