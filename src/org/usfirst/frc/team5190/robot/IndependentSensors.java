package org.usfirst.frc.team5190.robot;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class IndependentSensors implements Displayable {
	static private BuiltInAccelerometer accelerometer;

	static {
		accelerometer = new BuiltInAccelerometer();
	}

	public Accelerometer getAccelerometer() {
		return accelerometer;
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		LinkedList<Pair<String, Boolean>> result = new LinkedList<Pair<String, Boolean>>();
		return result;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Accelerometer X", accelerometer
				.getX()));
		result.add(new Pair<String, Double>("Accelerometer Y", accelerometer
				.getY()));
		result.add(new Pair<String, Double>("Accelerometer Z", accelerometer
				.getZ()));
		return result;
	}
}
