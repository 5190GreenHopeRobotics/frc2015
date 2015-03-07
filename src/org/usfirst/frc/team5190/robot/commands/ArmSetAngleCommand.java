package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSetAngleCommand extends Command {
	private double angle;
	private boolean waitToFinish;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	public ArmSetAngleCommand(double angle, boolean waitToFinish) {
		requires(armSubsystem);
		this.angle = angle;
		this.waitToFinish = waitToFinish;
	}

	protected void initialize() {
		armSubsystem.setArmAngle(angle);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		if (waitToFinish) {
			double topAngleRange = angle + 1;
			double lowerAngleRange = angle - 1;
			return lowerAngleRange < armSubsystem.getAngle()
					&& topAngleRange > armSubsystem.getAngle();
		}
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
