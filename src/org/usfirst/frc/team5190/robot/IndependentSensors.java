package org.usfirst.frc.team5190.robot;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.sensorFilter.AccelerometerFilter;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class IndependentSensors implements Displayable {
	static private BuiltInAccelerometer accelerometer;
	static private AccelerometerFilter accel;
	static private Ultrasonic ultraSonicSensor;;
	static {
		accelerometer = new BuiltInAccelerometer();
		accel = new AccelerometerFilter(accelerometer);
		ultraSonicSensor = new Ultrasonic(RobotMap.ULTRASONIC_PING,
				RobotMap.ULTRASONIC_RECIEVE);
		ultraSonicSensor.setEnabled(true);
	}

	public static Accelerometer getAccelerometer() {
		return accelerometer;
	}

	public static AccelerometerFilter getAccelFilter() {
		return accel;
	}

	public static Ultrasonic getUltraSonic() {
		return ultraSonicSensor;
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		LinkedList<Pair<String, Boolean>> result = new LinkedList<Pair<String, Boolean>>();
		return result;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Accelerometer X", accel.getX()));
		result.add(new Pair<String, Double>("Accelerometer Y", accel.getY()));
		result.add(new Pair<String, Double>("Accelerometer Z", accel.getZ()));
		result.add(new Pair<String, Double>("Ultrasonic Distance Inches:",
				ultraSonicSensor.getRangeInches()));
		return result;
	}
}