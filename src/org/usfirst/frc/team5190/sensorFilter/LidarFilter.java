package org.usfirst.frc.team5190.sensorFilter;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.PIDSource;

public class LidarFilter implements PIDSource {

	protected Lidar lidar;
	protected List<Integer> buffer;
	protected int windowSize;

	public LidarFilter(Lidar source) {
		lidar = source;
		buffer = new LinkedList<Integer>();
	}

	public void setWindow(int size) {
		windowSize = size;
	}

	public int getValue() {
		int sum = 0;
		for (Integer i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

	@Override
	public double pidGet() {
		if (buffer.size() > windowSize) {
			buffer.clear();
		}
		int distance = lidar.getDistance();
		int sum = 0;
		buffer.add(new Integer(distance));
		for (Integer i : buffer) {
			sum += i;
		}
		return sum / buffer.size();
	}

}
