package org.usfirst.frc.team5190.dashboard;

import java.util.LinkedList;

public class SmartDashBoardDisplayer {
	private LinkedList<Displayable> queue;
	private static SmartDashBoardDisplayer instance;
	private Display display = new SmartDashboardDisplay();

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
	public void addDisplayable(Displayable toAdd) {
		queue.add(toAdd);
	}

	/**
	 * display all added Displayable
	 */
	public void display() {
		for (Displayable iter : queue) {
			iter.displayValues(display);
		}
	}
}
