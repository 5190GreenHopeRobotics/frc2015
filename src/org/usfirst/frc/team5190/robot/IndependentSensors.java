package org.usfirst.frc.team5190.robot;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team5190.sensorFilter.AccelerometerFilter;
import org.usfirst.frc.team5190.sensorFilter.Lidar;
import org.usfirst.frc.team5190.sensorFilter.LidarFilter;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * This class contains all the sensors that needed to be shared between
 * subsystems
 * 
 * @author sdai
 *
 */
public class IndependentSensors implements Displayable {

	static private Map<String, PIDSource> sensors;
	static private BuiltInAccelerometer accelerometer;
	static private AccelerometerFilter accel;
	static private Lidar rawLidar;
	static private SerialPort serial;
	static private LidarFilter filteredLidar;
	static private AHRS navXSensor;
	static private PIDSource currentSpeedControl;
	static private PIDSource currentAngleControl;
	static {
		serial = new SerialPort(57600, SerialPort.Port.kMXP);
		sensors = new HashMap<String, PIDSource>();
		accelerometer = new BuiltInAccelerometer();
		accel = new AccelerometerFilter(accelerometer);
		rawLidar = new Lidar(Port.kMXP);
		filteredLidar = new LidarFilter(rawLidar);
		navXSensor = new AHRS(serial);
		currentSpeedControl = rawLidar;
		// currentAngleControl = gyro;
		loadSensor();
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

	public static AHRS getAHRS() {
		return navXSensor;
	}

	public static void setCurrentSpeedUnit(String name)
			throws UnsupportedSensorException {
		currentSpeedControl = sensors.get(name);
		if (currentSpeedControl == null) {
			currentSpeedControl = rawLidar;
			throw new UnsupportedSensorException(name + " is not supported");
		}
	}

	public static void setCurrentAngleControlUnit(String name)
			throws UnsupportedSensorException {
		currentAngleControl = sensors.get(name);
		if (currentAngleControl == null) {
			// currentAngleControl = gyro;
			throw new UnsupportedSensorException(name + " is not supported");
		}
	}

	public static LidarFilter getLidar() {
		return filteredLidar;
	}

	public static PIDSource getSpeedControlUnit() {
		return currentSpeedControl;
	}

	public static PIDSource getAngleControl() {
		return currentAngleControl;
	}

	public List<String> getSupportedSensors() {
		List<String> supported = new LinkedList<String>();
		for (Map.Entry<String, PIDSource> i : sensors.entrySet()) {
			supported.add(i.getKey());
		}
		return supported;
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
		result.addAll(filteredLidar.getDecimalValues());
		return result;
	}

	protected static void loadSensor() {
		sensors.put("lidar", rawLidar);
		sensors.put("navX", navXSensor);
	}
}
