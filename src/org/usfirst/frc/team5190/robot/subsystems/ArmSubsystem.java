package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmSubsystem extends Subsystem {

	private Solenoid armSolenoid = new Solenoid(RobotMap.ARM_SOLENOID_PORT);

	public void initDefaultCommand() {

	}

	/**
	 * Turns the arm on.
	 */
	public void extendArm() {
		armSolenoid.set(true);
	}

	/**
	 * Turns the arm off.
	 */
	public void stopArm() {
		armSolenoid.set(false);
	}

	public void retractArm() {
		armSolenoid.set(true);
		// There will be more content, right now, it just turns the arm on.
	}
}
