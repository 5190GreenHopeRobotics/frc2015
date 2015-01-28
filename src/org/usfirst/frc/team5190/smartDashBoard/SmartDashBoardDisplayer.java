package org.usfirst.frc.team5190.smartDashBoard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashBoardDisplayer {
	public static SmartDashBoardDisplayer getNewInstance() {
		return new SmartDashBoardDisplayer();
	}

	public void display(Displayable toDisplay) {
		if (toDisplay.getDecimalValues() != null) {
			for (Pair<String, Double> iter : toDisplay.getDecimalValues()) {
				SmartDashboard.putNumber(iter.first(), iter.second());
			}
		}
		if (toDisplay.getBooleanValue() != null) {
			for (Pair<String, Boolean> iter : toDisplay.getBooleanValue()) {
				SmartDashboard.putBoolean(iter.first(), iter.second());
			}
		}
	}
}
