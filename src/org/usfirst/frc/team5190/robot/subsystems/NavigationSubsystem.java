package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.sensor.Lidar;
import org.usfirst.frc.team5190.sensorFilter.VL6180xRangeFinder;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class NavigationSubsystem extends Subsystem implements Displayable {

	private static NavigationSubsystem instance;

	private VL6180xRangeFinder rangeFinderLeft, rangeFinderRight;
	private Lidar lidar;
	private AHRS navXSensor;

	private NavigationSubsystem() {
		super("NavigationSubsystem");
		// rangeFinderLeft = new VL6180xRangeFinder(
		// RobotMap.RANGE_FINDER_LEFT_PORT);
		// rangeFinderLeft.start();
		//
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
		SmartDashboard.putNumber("Left range finder", leftDistance);
		SmartDashboard.putNumber("Right rangeFinder", rightDistance);
		return (leftDistance + rightDistance) / 2;
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

	public RobotHeadingPIDSource createRobotHeadingPIDSource() {
		return new RobotHeadingPIDSource();
	}

	@Override
	// Display values
	public void displayValues(Display display) {
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
		display.putNumber("NavX Yaw", navXSensor.getYaw());
		display.putBoolean("NavX Calibrating", navXSensor.isCalibrating());
		display.putNumber("Lidar Distance", lidar.getDistance());
	}
}
