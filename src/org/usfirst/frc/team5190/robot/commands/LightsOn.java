package org.usfirst.frc.team5190.robot.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;

public class LightsOn extends Command {
	public Relay relay1 = new Relay(0, Relay.Direction.kForward);

	public LightsOn() {
		// Use requires() here to declare subsystem dependencies
		setTimeout(2);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	protected void execute() {
		while (true) {
			relay1.set(Relay.Value.kOn);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			relay1.set(Relay.Value.kOff);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}