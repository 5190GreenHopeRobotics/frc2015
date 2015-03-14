package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.sensor.Lidar;
import org.usfirst.frc.team5190.sensorFilter.VL6180xRangeFinder;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NavigationSubsystem extends Subsystem implements Displayable {

	private static NavigationSubsystem instance;

	private VL6180xRangeFinder rangeFinderLeft, rangeFinderRight;
	private Lidar lidar;
	private IMU navXSensor;

	private NavigationSubsystem() {
		super("NavigationSubsystem");
		rangeFinderLeft = new VL6180xRangeFinder(
				RobotMap.RANGE_FINDER_LEFT_PORT);
		rangeFinderLeft.start();

		rangeFinderRight = new VL6180xRangeFinder(
				RobotMap.RANGE_FINDER_RIGHT_PORT);
		rangeFinderRight.start();

		lidar = new Lidar(RobotMap.LIDAR_PORT);
		lidar.start();
		navXSensor = new AHRS(new SerialPort(57600, RobotMap.NAVX_PORT));

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
		// no default command for this subsystem
	}

	public int getPawlDistanceFromObject() {
		int leftDistance = rangeFinderLeft.getDistance();
		int rightDistance = rangeFinderRight.getDistance();
		return (leftDistance + rightDistance) / 2;
	}

	public double getLidarDistanceFromObject() {
		return lidar.getDistance();
	}

	public double getRoll() {
		return navXSensor.getRoll();
	}

	public double getPitch() {
		return navXSensor.getPitch();
	}

	public double getYaw() {
		return navXSensor.getYaw();
	}

	/**
	 * Reset the Yaw value to zero. Should be done periodically when at a known
	 * reference angle because the yaw will drift over time. The drift is
	 * approximently 1 degree a minute.
	 */
	public void zeroYaw() {
		navXSensor.zeroYaw();
	}

	public boolean isHeadingCalibrating() {
		return navXSensor.isCalibrating();
	}

	public RobotHeadingPIDSource createRobotHeadingPIDSource() {
		return new RobotHeadingPIDSource();
	}

	public LidarDistancePIDSource createLidarDistancePIDSource() {
		return new LidarDistancePIDSource();
	}

	@Override
	// Display values
	public void displayValues(Display display) {
		display.putNumber("NavX Yaw", navXSensor.getYaw());
		display.putBoolean("NavX Calibrating", navXSensor.isCalibrating());
		display.putNumber("Lidar Distance", lidar.getDistance());
		display.putNumber("Left Rangefinder", rangeFinderLeft.getDistance());
		display.putNumber("Right RangeFinder", rangeFinderRight.getDistance());
	}
}
