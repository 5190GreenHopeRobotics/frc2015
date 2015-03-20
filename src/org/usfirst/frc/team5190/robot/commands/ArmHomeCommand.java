package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem.SetArmAngle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHomeCommand extends Command {
	private final ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
	private SetArmAngle setArmHome;
	private double homeLevel = 320;
	private double homeToler = 3;

	public ArmHomeCommand() {
		requires(armSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setArmHome = armSubsystem.setArmAngle(homeLevel);
		setArmHome.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		setArmHome.execute();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double topAngleRange = homeLevel + homeToler;
		double lowerAngleRange = homeLevel - homeToler;
		return lowerAngleRange < armSubsystem.getAngle()
				&& topAngleRange > armSubsystem.getAngle();
	}

	// Called once after isFinished returns true
	protected void end() {
		setArmHome.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		setArmHome.end();
	}
}
