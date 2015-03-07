package org.usfirst.frc.team5190.robot.subsystems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.UnsupportedSensorException;
import org.usfirst.frc.team5190.sensor.Lidar;
import org.usfirst.frc.team5190.sensor.LidarFilter;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NavigationSubsystem extends Subsystem implements Displayable {

	private static NavigationSubsystem instance;

	private Map<String, PIDSource> sensors;
	private Lidar rawLidar;
	private SerialPort serial;
	private LidarFilter filteredLidar;
	private AHRS navXSensor;
	private PIDSource currentSpeedControl;
	private PIDSource currentAngleControl;

	private NavigationSubsystem() {
		sensors = new HashMap<String, PIDSource>();
		rawLidar = new Lidar(Port.kMXP);
		filteredLidar = new LidarFilter(rawLidar);
		navXSensor = new AHRS(serial);
		currentSpeedControl = rawLidar;
		// currentAngleControl = gyro;
		loadSensor();
	}

	public static NavigationSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new NavigationSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public AHRS getAHRS() {
		return navXSensor;
	}

	public void setCurrentSpeedUnit(String name)
			throws UnsupportedSensorException {
		currentSpeedControl = sensors.get(name);
		if (currentSpeedControl == null) {
			currentSpeedControl = rawLidar;
			throw new UnsupportedSensorException(name + " is not supported");
		}
	}

	public void setCurrentAngleControlUnit(String name)
			throws UnsupportedSensorException {
		currentAngleControl = sensors.get(name);
		if (currentAngleControl == null) {
			// currentAngleControl = gyro;
			throw new UnsupportedSensorException(name + " is not supported");
		}
	}

	public LidarFilter getLidar() {
		return filteredLidar;
	}

	public PIDSource getSpeedControlUnit() {
		return currentSpeedControl;
	}

	public PIDSource getAngleControl() {
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
	// Display values
	public void displayValues(Display display) {
		display.putNumber("NavX Altitude", navXSensor.getAltitude());
		display.putNumber("NavX BarometricPressure",
				navXSensor.getBarometricPressure());
		display.putNumber("NavX Magnetometer X",
				navXSensor.getCalibratedMagnetometerX());
		display.putNumber("NavX Magnetometer Y",
				navXSensor.getCalibratedMagnetometerY());
		display.putNumber("NavX Magnetometer Z",
				navXSensor.getCalibratedMagnetometerZ());
		display.putNumber("NavX Compass Heading",
				navXSensor.getCompassHeading());
		display.putNumber("NavX Displacement X", navXSensor.getDisplacementX());
		display.putNumber("NavX Displacement Y", navXSensor.getDisplacementY());
		display.putNumber("NavX fused heading", navXSensor.getFusedHeading());
		display.putNumber("NavX Pitch", navXSensor.getPitch());
		display.putNumber("NavX Roll", navXSensor.getRoll());
		display.putNumber("NavX Temperature", navXSensor.getTempC());
		display.putNumber("NavX Velocity X", navXSensor.getVelocityX());
		display.putNumber("NavX Velocity Y", navXSensor.getVelocityY());
		display.putNumber("NavX World Linear Accel X",
				navXSensor.getWorldLinearAccelX());
		display.putNumber("NavX World Linear Accel Y",
				navXSensor.getWorldLinearAccelY());
		display.putNumber("NavX World Linear Accel Z",
				navXSensor.getWorldLinearAccelZ());
		display.putNumber("NavX Yaw", navXSensor.getYaw());
	}

	private void loadSensor() {
		sensors.put("lidar", rawLidar);
		sensors.put("navX", navXSensor);
	}

}
