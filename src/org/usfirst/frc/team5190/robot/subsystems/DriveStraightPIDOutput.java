package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class DriveStraightPIDOutput implements PIDOutput {
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	DriveStraightPIDOutput() {
	}

	@Override
	public void pidWrite(double output) {
		driveTrainSubsystem.arcadeDrive(output, 0);
	}

}
