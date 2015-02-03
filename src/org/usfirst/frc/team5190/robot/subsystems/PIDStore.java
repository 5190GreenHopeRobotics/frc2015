package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class PIDStore implements PIDOutput {

	private boolean left;
	private PIDStore other;
	private RobotDrive drive;
	private double pidValue;

	public PIDStore(boolean left, RobotDrive drive) {
		this.left = left;
		this.other = other;
		this.drive = drive;
	}

	public synchronized double getPidValue() {
		return pidValue;
	}

	public void setOther(PIDStore other) {
		this.other = other;
	}

	@Override
	public void pidWrite(double output) {
		pidValue = output;
		if (left) {
			drive.tankDrive(output, other.getPidValue());
		} else {
			drive.tankDrive(other.getPidValue(), pidValue);
		}
	}

}
