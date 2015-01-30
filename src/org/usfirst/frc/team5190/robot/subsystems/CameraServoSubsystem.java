package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The servo which camera is mounted on
 */
public class CameraServoSubsystem extends Subsystem {

	public Servo cameraServo = new Servo(9);
	public int adder = 1;
	public double minAngle = 0;
	public double maxAngle = 359;
	public double defaultAngle = 180;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * reset the camera servo to 180 degree it is the default
	 */
	public void setZero() {
		// Reset Camera For Teleoperated Mode
		cameraServo.setAngle(180);
	}

	/**
	 * scan the field via turn the camera
	 */
	public void scanField() {

		System.out.println("Called");

		for (defaultAngle = 180; defaultAngle < 360; defaultAngle++) {

			cameraServo.setAngle(defaultAngle);

		}

	}

	/**
	 * TeleOperated period
	 */
	public void moveCamera(int angle) {
		cameraServo.setAngle(angle);
	}

}
