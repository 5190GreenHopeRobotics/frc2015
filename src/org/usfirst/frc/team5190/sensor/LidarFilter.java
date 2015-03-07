package org.usfirst.frc.team5190.sensor;

import java.util.LinkedList;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;

import edu.wpi.first.wpilibj.PIDSource;

public class LidarFilter implements PIDSource, Displayable {

	protected Lidar lidar;
	protected LinkedList<Double> buffer;
	protected int windowSize = 50;

	/**
	 * set the source for the filter
	 * 
	 * @param source
	 *            the lidar
	 */
	public LidarFilter(Lidar source) {
		lidar = source;
		lidar.start();
		buffer = new LinkedList<Double>();
	}

	/**
	 * set the size for averaging
	 * 
	 * @param size
	 *            the size of the window
	 */
	public void setWindow(int size) {
		windowSize = size;
	}

	/**
	 * get current average
	 * 
	 * @return the average
	 */
	public double getValue() {
		double sum = 0;
		for (Double i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

	/**
	 * update the window
	 */
	public void update() {
		if (buffer.size() > windowSize) {
			buffer.pop();
		}
		int distance = lidar.getDistance();
		buffer.add(new Double(distance));
	}

	@Override
	public double pidGet() {
		update();
		double sum = 0;
		for (Double i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

	@Override
	// Display Values
	public void displayValues(Display display) {
		// display.putNumber("Filtered Lidar", pidGet());
		// display.putNumber("Unfiltered Lidar", lidar.getDistance());
	}
}
