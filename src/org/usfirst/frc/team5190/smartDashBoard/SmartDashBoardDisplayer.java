package org.usfirst.frc.team5190.smartDashBoard;


public class SmartDashBoardDisplayer {
	private Thread worker;
	private DisplayableIterator r;

	public SmartDashBoardDisplayer() {
		r = new DisplayableIterator();
		worker = new Thread(r);
		worker.start();
	}

	public static SmartDashBoardDisplayer getNewInstance() {
		return new SmartDashBoardDisplayer();
	}

	public void display(Displayable toDisplay) {
		r.submit(toDisplay);
	}
}
