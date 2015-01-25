package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the forklift subsystem
 */
public class ForkliftSubsystem extends Subsystem {
	private TalonSRX forkLiftTalon = new TalonSRX(
			RobotMap.FORKLIFT_TALONSRX_PORT);
	private TalonSRX forkLiftRaiseTalon = new TalonSRX(
			RobotMap.FORKLIFT_TALONSRX_PORT);
	public Ultrasonic forkLiftUltrasonic = new Ultrasonic(1, 1);

	public void initDefaultCommand() {

	}

	public ForkliftSubsystem() {
		forkLiftUltrasonic.setEnabled(true);
		forkLiftUltrasonic.setAutomaticMode(true);
	}

	/**
	 * Opens the grabber. Sets the speed
	 */
	public void openGrabber() {
		forkLiftTalon.set(1);
	}

	/**
	 * Close the grabber
	 */
	public void closeGrabber() {
		forkLiftTalon.set(-1);
	}

	/**
	 * Stop the grabber
	 */
	public void stopGrabber() {
		forkLiftTalon.set(0);
	}

	/**
	 * raise the forklift at full speed
	 */
	public void raiseGrabber() {
		forkLiftRaiseTalon.set(1);
	}

	/**
	 * lower the forklift at full speed
	 */
	public void lowerGrabber() {
		forkLiftRaiseTalon.set(-1);
	}

	/**
	 * set the forklift speed to 0
	 */
	public void stopraiseGrabber() {
		forkLiftRaiseTalon.set(0);
	}
}
