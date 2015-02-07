package org.usfirst.frc.team5190.sensorFilter;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * a filter class that will make the accelerometer less sensitive, according to
 * apple sdk
 * 
 * @author sdai
 *
 */
public class AccelerometerFilter {
	protected Accelerometer sensor;
	protected static double FilteringFactor = 0.1;
	protected double[] accel;

	/**
	 * set the filtering factor, used for tuning
	 * 
	 * @param factor
	 *            the factor to set
	 */
	public static void setFilteringFactor(double factor) {
		FilteringFactor = factor;
	}

	public AccelerometerFilter(Accelerometer target) {
		sensor = target;
		accel = new double[3];
	}

	/**
	 * get the filtered value for x
	 * 
	 * @return the filtered value
	 */

	public double getX() {
		double x;
		x = sensor.getX();
		x = filter(x, 0);
		return x;
	}

	/**
	 * get the filtered value for y
	 * 
	 * @return the y value
	 */
	public double getY() {
		double y;
		y = sensor.getY();
		y = filter(y, 1);
		return y;
	}

	/**
	 * get the filtered value for z
	 * 
	 * @return the z value
	 */
	public double getZ() {
		double z;
		z = sensor.getZ();
		z = filter(z, 2);
		return z;
	}

	/**
	 * the filtering algorithm
	 * 
	 * @param raw
	 *            the raw value from accelerometer
	 * @param filterValue
	 *            the filtering value
	 * @return the filtered value
	 */
	protected double filter(double raw, int index) {
		double result;
		accel[index] = raw * FilteringFactor + accel[index]
				* (0.1 - accel[index]);
		result = raw - accel[index];
		return result;
	}

}