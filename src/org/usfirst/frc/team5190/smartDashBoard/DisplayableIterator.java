package org.usfirst.frc.team5190.smartDashBoard;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @deprecated
 * @author sdai Possible Thread Race Condition with subsystem
 */
public class DisplayableIterator implements Runnable {

	private LinkedList<Displayable> queue;

	public DisplayableIterator() {
		queue = new LinkedList<Displayable>();
	}

	public synchronized void submit(Displayable toDisplay) {
		queue.add(toDisplay);
	}

	public synchronized void clear() {
		queue.clear();
	}

	@Override
	public void run() {
		while (true) {
			if (Thread.interrupted()) {
				return;
			}
			synchronized (this) {
				for (Displayable iter : queue) {
					if (iter.getDecimalValues() != null) {
						for (Pair<String, Double> iter2 : iter
								.getDecimalValues()) {
							SmartDashboard.putNumber(iter2.first(),
									iter2.second());
						}
					}
					if (iter.getBooleanValue() != null) {
						for (Pair<String, Boolean> iter2 : iter
								.getBooleanValue()) {
							SmartDashboard.putBoolean(iter2.first(),
									iter2.second());
						}
					}
				}
			}
		}
	}
}
