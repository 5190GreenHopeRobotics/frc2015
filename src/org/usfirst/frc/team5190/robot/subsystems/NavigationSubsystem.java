package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.sensorFilter.RangeFinder;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NavigationSubsystem extends Subsystem {

	private static NavigationSubsystem instance;
	private RangeFinder rangeFinder;

	private NavigationSubsystem() {
		rangeFinder = new RangeFinder(Port.kMXP);
		rangeFinder.start();
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
		return rangeFinder.getDistance();
	}

}
