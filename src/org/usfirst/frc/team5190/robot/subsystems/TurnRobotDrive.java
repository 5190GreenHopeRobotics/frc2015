package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class TurnRobotDrive implements PIDOutput {
	private RobotDrive robotDrive;

	TurnRobotDrive(RobotDrive robotDrive) {
		this.robotDrive = robotDrive;
	}

	public void turn(double rotateMagnitude) {
		robotDrive.arcadeDrive(0, rotateMagnitude);
	}

	@Override
	public void pidWrite(double output) {
		turn(output);
	}
}