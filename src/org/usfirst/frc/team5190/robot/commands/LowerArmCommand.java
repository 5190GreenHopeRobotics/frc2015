package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command requires the armSubsystem.
 */
public class LowerArmCommand extends Command {

	public LowerArmCommand() {
		requires(Robot.armSubsystem);
		// setTimeout(1.0);
	}

	/**
	 * This starts the lowering of the arm. The arm only lowers if the current
	 * degrees is > 0.
	 */
	@Override
	protected void initialize() {
		if (Robot.armSubsystem.armLengthEncoder.getDirection() == false) {
			Robot.armSubsystem.armLengthEncoder.reset();
		}
		Robot.armSubsystem.currentdegrees = Robot.armSubsystem.currentdegrees
				- (Robot.armSubsystem.armLengthEncoder.getDistance()
						/ Robot.armSubsystem.shaftcircumference * 360);
		if (Robot.armSubsystem.currentdegrees > 0) {
			Robot.armSubsystem.lowerArm();
		}
	}

	/**
	 * Called repeatedly when this Command is scheduled to run
	 */
	@Override
	protected void execute() {
	}

	/**
	 * This returns when the time is finished.
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}

	/**
	 * This stops the arm from lowering when this command ends, and also stops
	 * and resets the encoder.
	 */
	@Override
	protected void end() {
		Robot.armSubsystem.stopArmAngleChange();
		// Robot.armSubsystem.armLengthEncoder.reset();
	}

	/**
	 * This stops the arm from lowering when the code is interrupted. This will
	 * also cause the encoder to reset and stop.
	 */
	@Override
	protected void interrupted() {
		Robot.armSubsystem.stopArmAngleChange();
		// Robot.armSubsystem.armLengthEncoder.reset();
	}
}
