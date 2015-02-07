package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PneumaticsProofOfConcept extends Command {
	Solenoid ExampleSolenoid;

	// Compressor ExampleCompressor;

	public PneumaticsProofOfConcept() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		ExampleSolenoid = new Solenoid(0);
		// ExampleCompressor = new Compressor(1);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		ExampleSolenoid.set(true);
		// ExampleCompressor.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
