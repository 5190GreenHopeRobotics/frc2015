package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimedRaiseLowerArmCommand extends Command {

	private boolean raise;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	public TimedRaiseLowerArmCommand(double time, boolean raise) {
		requires(armSubsystem);
		setTimeout(time);
		this.raise = raise;
	}

	@Override
	protected void initialize() {
		if (raise) {
			armSubsystem.raiseArm();
		} else {
			armSubsystem.lowerArm();
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		armSubsystem.stopArm();
	}

	@Override
	protected void interrupted() {
		armSubsystem.stopArm();
	}
}
