package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;

public class LightsOn extends Command {
	public Relay relay1 = new Relay(0, Relay.Direction.kForward);
	private Thread lightThread;
	private Object lock;

	public LightsOn() {
		// Use requires() here to declare subsystem dependencies
		// lock = new Object();
		// lightThread = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// while (true) {
		// synchronized (lock) {
		// relay1.set(Relay.Value.kOn);
		// try {
		// TimeUnit.MILLISECONDS.sleep(500);
		// } catch (InterruptedException e) {
		// return;
		// }
		// relay1.set(Relay.Value.kOff);
		// try {
		// TimeUnit.MILLISECONDS.sleep(500);
		// } catch (InterruptedException e) {
		// return;
		// }
		// }
		//
		// }
		// }
		// });
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	protected void execute() {
		// lightThread.start();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		lightThread.interrupt();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
