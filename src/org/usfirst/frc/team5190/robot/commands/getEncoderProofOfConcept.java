package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class getEncoderProofOfConcept extends Command {
	private Encoder testEncoder;
	boolean isFinished = false;
	int counter = 0;

	public getEncoderProofOfConcept() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		testEncoder = new Encoder(3, 4);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		testEncoder.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		counter++;
		if (counter % 50 == 0) {
			@SuppressWarnings("unused")
			// int test1 = testEncoder.get();
			boolean test2 = testEncoder.getDirection();
			double test3 = testEncoder.getDistance();
			double test4 = testEncoder.getRate();
			System.err.println("Direction:" + test2);
			System.err.println("Rate:" + test4);
			System.err.println("Distance:" + test3);
		}

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
