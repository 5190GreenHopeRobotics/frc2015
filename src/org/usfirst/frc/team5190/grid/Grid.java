package org.usfirst.frc.team5190.grid;

import org.usfirst.frc.team5190.smartDashBoard.Pair;

public class Grid {
	protected double currentX;
	protected double currentY;
	protected double currentVel;
	protected Thread updater;

	public Grid(Double initX, Double initY) {
		currentX = initX.doubleValue();
		currentY = initY.doubleValue();
	}

	public Pair<Double, Double> getCoordinate() {
		Pair<Double, Double> coord = new Pair<Double, Double>();
		synchronized (this) {
			coord.setFirst(currentX);
			coord.setSecond(currentY);
		}
		return coord;
	}

	protected void update() {

	}

	protected double getDistance(double v, double acceleration, double time) {
		return v * time + (acceleration * time * time / 2);
	}

	protected double getVelocity(double v, double accel, double time) {
		return v + accel * time;
	}
}
