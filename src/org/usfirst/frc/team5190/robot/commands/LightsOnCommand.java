package org.usfirst.frc.team5190.robot.commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;

/**
 * turn on/off led
 * 
 * @author sdai
 */
public class LightsOnCommand extends Command {
	public Relay relay1 = new Relay(0, Relay.Direction.kForward);
	// thread pool for concurrency of led and robot
	private ExecutorService lightThread;
	// the lock
	private Object lock;
	private boolean isOn = false;

	public LightsOnCommand() {
		lock = new Object();

	}

	@Override
	protected void initialize() {
	}

	/**
	 * turn on led if off, off if on
	 */
	@Override
	protected void execute() {
		System.out.println(isOn);
		if (!isOn) {
			lightThread = Executors.newCachedThreadPool();
			lightThread.execute(new Runnable() {

				@Override
				public void run() {
					while (true) {
						synchronized (lock) {
							relay1.set(Relay.Value.kOn);
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								return;
							}
							relay1.set(Relay.Value.kOff);
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								return;
							}
						}

					}

				}

			});
			isOn = true;
		} else {
			lightThread.shutdownNow();
			relay1.set(Relay.Value.kOff);
			isOn = false;
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	/**
	 * shutdown all thread
	 */
	@Override
	protected void end() {
		lightThread.shutdownNow();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
