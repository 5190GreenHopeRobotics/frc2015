package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class TurnPIDOutput implements PIDOutput {
	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();

	TurnPIDOutput() {
	}

	@Override
	public void pidWrite(double output) {
		driveTrainSubsystem.arcadeDrive(0.0, -output);
	}

}
