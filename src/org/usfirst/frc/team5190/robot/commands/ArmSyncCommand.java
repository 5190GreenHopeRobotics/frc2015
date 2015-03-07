package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSyncCommand extends Command {

	protected ArmSubsystem arm = ArmSubsystem.getInstance();
	protected boolean reachedBottom = false;
	protected boolean reachedTop = false;
	protected int armLowAngle;
	protected int armHighAngle;

	public ArmSyncCommand() {
		requires(arm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!reachedBottom) {
			reachedBottom = arm.goToLowest();
			if (reachedBottom) {
				armLowAngle = (int) arm.getAngle();
			}
		} else if (reachedBottom) {
			reachedTop = arm.goToHighest();
			if (reachedTop) {
				armHighAngle = (int) arm.getAngle();
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return reachedBottom && reachedTop;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		arm.setLimit(armLowAngle, armHighAngle);
		arm.setArmAngle(armLowAngle + 5);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
