package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;


import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotGrabberSubsystem extends Subsystem {
	private TalonSRX grabberTalon = new TalonSRX(RobotMap.GRABBER_TALONSRX_PORT);
	public Ultrasonic grabberUltrasonic = new Ultrasonic(1,1);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// Opens the grabber. Sets the speed 
	public void openGrabber() {
			grabberTalon.set(1);
	}
	// Close the grabber
	public void closeGrabber() {
			grabberTalon.set(-1);
	}
	
	// Stop the grabber
	public void stopGrabber() {
			grabberTalon.set(0);
	}
}
