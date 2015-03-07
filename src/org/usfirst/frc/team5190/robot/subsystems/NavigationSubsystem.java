package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.sensorFilter.VL6180xRangeFinder;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class NavigationSubsystem extends Subsystem {

	private static NavigationSubsystem instance;
	private VL6180xRangeFinder rangeFinderLeft, rangeFinderRight;

	private NavigationSubsystem() {
		rangeFinderLeft = new VL6180xRangeFinder(Port.kMXP);
		rangeFinderLeft.start();
		
		rangeFinderRight = new VL6180xRangeFinder(Port.kOnboard);
		rangeFinderRight.start();

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
	
	public int getPawlDistanceFromObject(){
		int leftDistance = rangeFinderLeft.getDistance();
		int rightDistance = rangeFinderRight.getDistance();
		SmartDashboard.putNumber("Left range finder", leftDistance);
		SmartDashboard.putNumber("Right rangeFinder", rightDistance);
		return rangeFinderLeft.getDistance();
	}

}
