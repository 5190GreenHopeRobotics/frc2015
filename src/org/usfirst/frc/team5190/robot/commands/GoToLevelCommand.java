package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem.SetArmAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoToLevelCommand extends Command {
	private final ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
	private static final int[] levels = { 7, 80, 152, 225, 287 };
	private static final int level0 = 7;
	private static final int level1 = 80;
	private static final int level2 = 152;
	private static final int level3 = 225;
	private static final int level4 = 287;
	private double goToAngLevel;
	private static final double ARM_SET_ANGLE_TOLERANCE = 2;
	private double tolerance;
	private SetArmAngle setArmAngle;
	private Preferences prefs = Preferences.getInstance();

	public GoToLevelCommand(boolean goUp) {
		requires(armSubsystem);
		if (goUp) {
			goToAngLevel = levelup();
		} else {
			goToAngLevel = leveldown();
		}
	}

	public GoToLevelCommand(int goToLevel) {
		requires(armSubsystem);
		goToAngLevel = levels[goToLevel];

	}

	public double levelup() {
		double nextLevel;

		if (armSubsystem.getAngle() < level1) {

			nextLevel = level1;

		} else if (armSubsystem.getAngle() < level2) {

			nextLevel = level2;

		} else if (armSubsystem.getAngle() < level3) {

			nextLevel = level3;

		} else {

			nextLevel = level4;

		}
		System.out.println("SetAngle To: " + nextLevel);
		return nextLevel;
	}

	public double leveldown() {
		double previouslevel;

		if (armSubsystem.getAngle() > level3) {

			previouslevel = level3;

		} else if (armSubsystem.getAngle() > level2) {

			previouslevel = level2;

		} else if (armSubsystem.getAngle() > level1) {

			previouslevel = level1;

		} else {

			previouslevel = level0;

		}
		System.out.println("SetAngle To: " + previouslevel);
		return previouslevel;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		tolerance = prefs.getDouble("arm.set.angle.tolerance",
				ARM_SET_ANGLE_TOLERANCE);
		setArmAngle = armSubsystem.setArmAngle(goToAngLevel);
		setArmAngle.start();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		setArmAngle.execute();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double topAngleRange = goToAngLevel + tolerance;
		double lowerAngleRange = goToAngLevel - tolerance;
		return lowerAngleRange < armSubsystem.getAngle()
				&& topAngleRange > armSubsystem.getAngle();
	}

	// Called once after isFinished returns true
	protected void end() {
		setArmAngle.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		setArmAngle.end();
	}
}
