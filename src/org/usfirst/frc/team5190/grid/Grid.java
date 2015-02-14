package org.usfirst.frc.team5190.grid;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team5190.robot.IndependentSensors;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

public class Grid {
	protected double currentX;
	protected double currentY;
	protected double currentVel;
	protected int updateInterval;
	protected Thread updater;

	public Grid(Double initX, Double initY) {
		currentX = initX.doubleValue();
		currentY = initY.doubleValue();
		updateInterval = 10;
		updater = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted())
					update();

			}

		});
		updater.start();
	}

	public Pair<Double, Double> getCoordinate() {
		Pair<Double, Double> coord = new Pair<Double, Double>();
		synchronized (this) {
			coord.setFirst(currentX);
			coord.setSecond(currentY);
		}
		return coord;
	}

	protected synchronized void update() {
		double bufferX;
		double angleRadian = (Math.PI * IndependentSensors.getGyro().getAngle() / 180);
		try {
			TimeUnit.MILLISECONDS.sleep(updateInterval);
		} catch (InterruptedException e) {
			return;
		}
		bufferX = getDistance(currentVel, IndependentSensors.getAccelerometer()
				.getX() * 32.174 * 3, updateInterval / 1000)
				+ currentX;
		currentVel = getVelocity(currentVel, IndependentSensors
				.getAccelerometer().getX() * 32.174 * 3, updateInterval / 1000);
		currentX = Math.cos(angleRadian) * bufferX;
		currentY = Math.sin(angleRadian) * bufferX;

	}

	protected double getDistance(double v, double acceleration, double time) {
		return v * time + (acceleration * time * time / 2);
	}

	protected double getVelocity(double v, double accel, double time) {
		return v + accel * time;
	}
}
