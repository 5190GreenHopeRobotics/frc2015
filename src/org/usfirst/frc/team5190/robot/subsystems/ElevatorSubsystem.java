package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the elevator subsystem
 */
public class ElevatorSubsystem extends Subsystem {
	// assumes victor is located in port 5
	private Victor elevatorControl = new Victor(5);

	// default constructor
	public ElevatorSubsystem() {
	}

	// normal constructor
	public void raiseElevator() {
		elevatorControl.set(.5);
	}

	/**
	 * lower the elevator at half speed
	 */
	public void lowerElevator() {
		elevatorControl.set(-.5);
	}

	/**
	 * set the elevator speed to 0
	 */
	public void stopElevator() {
		elevatorControl.set(0);
	}

	protected void initDefaultCommand() {

	}
}
