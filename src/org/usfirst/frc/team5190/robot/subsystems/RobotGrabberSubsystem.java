package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotGrabberSubsystem extends Subsystem {

	private Solenoid Grabbersolenoid = new Solenoid(
			RobotMap.GRABBER_SOLENOID_PORT);
	Compressor Compressor1 = new Compressor();

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// Opens the grabber
	public void Opengrabber() {
			Grabbersolenoid.set(true);
	}
 
	// Close the grabber
	public void Closegrabber() {
			Grabbersolenoid.set(true);
	}
	
	public void Stopgrabber() {
			Grabbersolenoid.set(false);
	}
}
