package org.usfirst.frc.team5190.sensorFilter;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class AccelerometerFilter {
	protected Accelerometer sensor;
	protected static double FilteringFactor = 0;
	protected double[] accel;

	public static void setFilteringFactor(double factor) {
		FilteringFactor = factor;
	}

	public AccelerometerFilter(Accelerometer target) {
		sensor = target;
		accel = new double[3];
	}

	public double getX() {
		double x;
		x = sensor.getX();
		x = filter(x, accel[0]);
		return x;
	}

	public double getY() {
		double y;
		y = sensor.getY();
		y = filter(y, accel[1]);
		return y;
	}

	public double getZ() {
		double z;
		z = sensor.getZ();
		z = filter(z, accel[2]);
		return z;
	}

	protected double filter(double raw, double filterValue) {
		double result;
		filterValue = raw * FilteringFactor + filterValue * (0.1 - filterValue);
		result = raw - filterValue;
		return result;
	}

}
