package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveStraightRobotDrive implements PIDOutput {
	// private Gyro gyro = IndependentSensors.getGyro();
	private RobotDrive robotDrive;

	private static final double Kp = .03;

	DriveStraightRobotDrive(RobotDrive robotDrive) {
		this.robotDrive = robotDrive;
	}

	public void drive(double outputMagnitude) {
		// double angle = gyro.getAngle(); // get current heading
		// robotDrive.drive(outputMagnitude, -angle * Kp); // drive towards
		// heading
		robotDrive.drive(outputMagnitude, 0);
	}

	@Override
	public void pidWrite(double output) {
		drive(output);
	}
}
