package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ForkliftSubsystem extends Subsystem {
	private TalonSRX forkLiftTalon = new TalonSRX(
			RobotMap.FORKLIFT_TALONSRX_PORT); // Open and close the forklift
	private TalonSRX forkLiftTalon2 = new TalonSRX(
			RobotMap.FORKLIFT_TALONSRX_PORT2); // Raise and lower the forklift
	public Ultrasonic forkLiftUltrasonic = new Ultrasonic(1, 1);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public ForkliftSubsystem() {
		forkLiftUltrasonic.setEnabled(true);
		forkLiftUltrasonic.setAutomaticMode(true);
	}

	// Opens the grabber. Sets the speed
	public void openGrabber() {
		forkLiftTalon.set(1);
	}

	// Close the grabber
	public void closeGrabber() {
		forkLiftTalon.set(-1);
	}

	// Stop the grabber
	public void stopGrabber() {
		forkLiftTalon.set(0);
	}

	public void raiseGrabber() {
		forkLiftTalon2.set(1);
	}

	public void lowerGrabber() {
		forkLiftTalon2.set(1);
	}

	public void stopraiseGrabber() {
		forkLiftTalon2.set(0);
	}
}
