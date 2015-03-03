package org.usfirst.frc.team5190.grid;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.IndependentSensors;
import org.usfirst.frc.team5190.util.Pair;

public class Grid implements Displayable {
	protected double currentX;
	protected double currentY;
	protected double currentVel;
	protected int updateInterval;
	protected Thread updater;
	protected double distanceTraveled;

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
		double angleRadian = 0.0;
		// double angleRadian = (Math.PI *
		// IndependentSensors.getGyro().getAngle() / 180);
		try {
			TimeUnit.MILLISECONDS.sleep(updateInterval);
		} catch (InterruptedException e) {
			return;
		}
		distanceTraveled = getDistance(currentVel, IndependentSensors
				.getAccelerometer().getX() * 32.174 * 3, updateInterval / 1000);
		currentVel = getVelocity(currentVel, IndependentSensors
				.getAccelerometer().getX() * 32.174 * 3, updateInterval / 1000);
		currentX = Math.cos(angleRadian) * distanceTraveled + currentX;
		currentY = Math.sin(angleRadian) * distanceTraveled + currentY;

	}

	protected double getDistance(double v, double acceleration, double time) {
		return v * time + (acceleration * time * time / 2);
	}

	protected double getVelocity(double v, double accel, double time) {
		return v + accel * time;
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("X Grid Value", getCoordinate().first());
		display.putNumber("Y Grid Value", getCoordinate().second());
	}
}
