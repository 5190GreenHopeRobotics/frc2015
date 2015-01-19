package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5190.robot.RobotMap;

/**
 *
 */
public class RobotPawSubsystem extends Subsystem {
	private TalonSRX pawTalon = new TalonSRX(RobotMap.PAW_TALONSRX_PORT);
	public Ultrasonic pawUltrasonic = new Ultrasonic(1,1);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public RobotPawSubsystem() {
		pawUltrasonic.setEnabled(true);
		pawUltrasonic.setAutomaticMode(true);
	}

	// Opens the grabber. Sets the speed 
	public void openGrabber() {
			pawTalon.set(1);
	}
	// Close the grabber
	public void closeGrabber() {
			pawTalon.set(-1);
	}
	
	// Stop the grabber
	public void stopGrabber() {
			pawTalon.set(0);
	}
}
