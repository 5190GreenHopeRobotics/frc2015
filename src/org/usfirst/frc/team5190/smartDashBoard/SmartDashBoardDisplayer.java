package org.usfirst.frc.team5190.smartDashBoard;

public class SmartDashBoardDisplayer {
	private Thread worker;
	private DisplayableIterator r;
	private static SmartDashBoardDisplayer instance;

	static {
		instance = new SmartDashBoardDisplayer();
	}

	private SmartDashBoardDisplayer() {
		r = new DisplayableIterator();
		worker = new Thread(r);
		worker.start();
	}

	public static SmartDashBoardDisplayer getInstance() {
		return instance;
	}

	public void display(Displayable toDisplay) {
		r.submit(toDisplay);
	}
}
