package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotGrabberSubsystem extends Subsystem {
	private final boolean ON = true;
	private final boolean OFF = false;
	private Solenoid grabberSolenoid = new Solenoid(RobotMap.GRABBER_SOLENOID_PORT);
	private Compressor compressor1 = new Compressor();

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
//	public void robotGrabberSubsystem(){
//		
//	}

	// Opens the grabber. Now it just turns the grabber on, more will be added when I know what I'm doing
	public void openGrabber() {
			grabberSolenoid.set(ON);
	}
 
	// Close the grabber
	public void closeGrabber() {
			grabberSolenoid.set(ON);
	}
	
	// Stop the grabber
	public void stopGrabber() {
			grabberSolenoid.set(OFF);
	}
}
