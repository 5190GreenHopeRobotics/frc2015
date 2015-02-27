package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem.SetArmAngle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSetAngleCommand extends Command {
	private double angle;
	private SetArmAngle setArmAngle;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	public ArmSetAngleCommand(double angle) {
		requires(armSubsystem);
		this.angle = angle;
	}

	protected void initialize() {
		setArmAngle = armSubsystem.setArmAngle();
		setArmAngle.start(angle);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return setArmAngle.reachedAngle();
	}

	protected void end() {
		armSubsystem.stopArm();
	}

	protected void interrupted() {
		end();
	}
}
