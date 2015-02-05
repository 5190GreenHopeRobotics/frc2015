package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NavigationSubsystem extends Subsystem {

	private static Ultrasonic ultrasonicSensor1 = new Ultrasonic(1, 2);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * @return double value in Inches of distance from target
	 */
	public double getUltrasonic() {
		return ultrasonicSensor1.getRangeInches();
	}
}
