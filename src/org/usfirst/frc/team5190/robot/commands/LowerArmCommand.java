package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command requires the armSubsystem.
 */
public class LowerArmCommand extends Command {

	public LowerArmCommand() {
		requires(Robot.armSubsystem);
	}

	/**
	 * This starts the lowering of the arm. The arm only lowers if the current
	 * degrees is > 0.
	 */
	protected void initialize() {
		if (Robot.armSubsystem.getencoderangle() > 0
				&& Robot.armSubsystem.getlowerarmlimitswitch() == true) {
			Robot.armSubsystem.lowerArm();
		}
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {
	}

	/**
	 * This returns when the time is finished.
	 */
	protected boolean isFinished() {
		return true;
	}

	/**
	 * This stops the arm from lowering when this command ends, and also stops
	 * and resets the encoder.
	 */
	protected void end() {
		Robot.armSubsystem.stopArmAngleChange();
	}

	/**
	 * This stops the arm from lowering when the code is interrupted. This will
	 * also cause the encoder to reset and stop.
	 */
	protected void interrupted() {
		Robot.armSubsystem.stopArmAngleChange();
	}
}
