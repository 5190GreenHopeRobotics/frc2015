package org.usfirst.frc.team5190.robot;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.sensorFilter.AccelerometerFilter;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * This class contains all the sensors that needed to be shared between
 * subsystems
 * 
 * @author sdai
 *
 */
public class IndependentSensors implements Displayable {
	static private BuiltInAccelerometer accelerometer;
	static private AccelerometerFilter accel;
	static private Ultrasonic ultraSonicSensor;
	static private Gyro gyro;
	static {
		accelerometer = new BuiltInAccelerometer();
		accel = new AccelerometerFilter(accelerometer);
		gyro = new Gyro(RobotMap.GYRO_PORT);
		gyro.initGyro();
		gyro.reset();
		
		// ultraSonicSensor = new Ultrasonic(RobotMap.ULTRASONIC_PING,
		// RobotMap.ULTRASONIC_RECIEVE);
		// ultraSonicSensor.setEnabled(true);
	}

	/**
	 * get the accelerometer stored in this class
	 * 
	 * @return
	 */
	public static Accelerometer getAccelerometer() {
		return accelerometer;
	}

	/**
	 * get the accelerometer filter
	 * 
	 * @return
	 */
	public static AccelerometerFilter getAccelFilter() {
		return accel;
	}

	/**
	 * get the ultrasonic sensors stored
	 * @deprecated
	 * @return
	 */
	public static Ultrasonic getUltraSonic() {
		return ultraSonicSensor;
	}

	public static Gyro getGyro() {
		return gyro;
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		return null;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Accelerometer X", accel.getX()));
		result.add(new Pair<String, Double>("Accelerometer Y", accel.getY()));
		result.add(new Pair<String, Double>("Accelerometer Z", accel.getZ()));
		result.add(new Pair<String, Double>("Gyro:", gyro.getAngle()));
		return result;
	}
}