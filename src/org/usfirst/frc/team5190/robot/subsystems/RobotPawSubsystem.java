package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotPawSubsystem extends Subsystem {
	private TalonSRX pawopenTalon = new TalonSRX(RobotMap.PAWOPEN_TALONSRX_PORT);
	private TalonSRX pawraiseTalon = new TalonSRX(
			RobotMap.PAWRAISE_TALONSRX_PORT);
	public Ultrasonic pawUltrasonic = new Ultrasonic(1, 1);

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
		pawopenTalon.set(1);
	}

	// Close the grabber
	public void closeGrabber() {
		pawopenTalon.set(-1);
	}

	// Stop the grabber
	public void stopopenGrabber() {
		pawopenTalon.set(0);
	}

	public void raiseGrabber() {
		pawraiseTalon.set(1);
	}

	public void lowerGrabber() {
		pawraiseTalon.set(1);
	}

	public void stopraiseGrabber() {
		pawraiseTalon.set(0);
	}
}
