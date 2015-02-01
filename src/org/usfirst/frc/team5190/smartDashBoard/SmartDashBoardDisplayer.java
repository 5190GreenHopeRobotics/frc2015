package org.usfirst.frc.team5190.smartDashBoard;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashBoardDisplayer {
	private LinkedList<Displayable> queue;
	private static SmartDashBoardDisplayer instance;

	static {
		instance = new SmartDashBoardDisplayer();
	}

	private SmartDashBoardDisplayer() {
		queue = new LinkedList<Displayable>();
	}

	/**
	 * singlinton design pattern, get one instance of this class
	 * 
	 * @return the instance
	 */
	public static SmartDashBoardDisplayer getInstance() {
		return instance;
	}

	/**
	 * adds a Displayable to be scheduled to be displayed
	 * 
	 * @param toAdd
	 *            the Displayable to be added
	 */
	public void submit(Displayable toAdd) {
		queue.add(toAdd);
	}

	/**
	 * display all added Displayable
	 */
	public void display() {
		for (Displayable iter : queue) {
			if (iter.getDecimalValues() != null) {
				for (Pair<String, Double> iter2 : iter.getDecimalValues()) {
					SmartDashboard.putNumber(iter2.first(), iter2.second());
				}
			}
			if (iter.getBooleanValue() != null) {
				for (Pair<String, Boolean> iter2 : iter.getBooleanValue()) {
					SmartDashboard.putBoolean(iter2.first(), iter2.second());
				}
			}
		}
	}
}
