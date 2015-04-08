package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem.SetArmAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSetAngleCommand extends Command {
	private static final double ARM_SET_ANGLE_TOLERANCE = 2;
	private double angle;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
	private double tolerance;
	private SetArmAngle setArmAngle;

	private Preferences prefs = Preferences.getInstance();

	public ArmSetAngleCommand(double angle) {
		requires(armSubsystem);
		this.angle = angle;
		setTimeout(2);
	}

	protected void initialize() {
		tolerance = prefs.getDouble("arm.set.angle.tolerance",
				ARM_SET_ANGLE_TOLERANCE);
		setArmAngle = armSubsystem.setArmAngle(angle);
		setArmAngle.start();
	}

	protected void execute() {
		setArmAngle.execute();
	}

	protected boolean isFinished() {
		double topAngleRange = angle + tolerance;
		double lowerAngleRange = angle - tolerance;
		return (lowerAngleRange < armSubsystem.getAngle() && topAngleRange > armSubsystem
				.getAngle()) || isTimedOut();

	}

	protected void end() {
		setArmAngle.end();
	}

	protected void interrupted() {
		end();
	}
}
