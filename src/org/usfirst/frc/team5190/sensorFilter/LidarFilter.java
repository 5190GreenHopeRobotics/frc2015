package org.usfirst.frc.team5190.sensorFilter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.PIDSource;

public class LidarFilter implements PIDSource, Displayable {

	protected Lidar lidar;
	protected List<Integer> buffer;
	protected int windowSize;

	/**
	 * set the source for the filter
	 * 
	 * @param source
	 *            the lidar
	 */
	public LidarFilter(Lidar source) {
		lidar = source;
		buffer = new LinkedList<Integer>();
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
	public int getValue() {
		int sum = 0;
		for (Integer i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

	public void update() {
		if (buffer.size() > windowSize) {
			buffer.clear();
		}
		int distance = lidar.getDistance();
		buffer.add(new Integer(distance));
	}

	@Override
	public double pidGet() {
		update();
		int sum = 0;
		for (Integer i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		List<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Filtered Lidar", pidGet()));
		result.add(new Pair<String, Double>("Unfiltered Lidar", new Double(
				lidar.getDistance())));
		return result;
	}
}
