package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class PIDRobotDrive extends RobotDrive implements PIDOutput {

	protected Gyro gyro;

	public PIDRobotDrive(Jaguar frontleft, Jaguar backleft, Jaguar frontright,
			Jaguar backright) {
		super(frontleft, backleft, frontright, backright);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pidWrite(double output) {
		this.drive(output, 0.0);
		// this.drive(output, -gyro.getAngle() * DriveTrainSubsystem.kP);
	}

	public void setGyro(Gyro toSet) {
		gyro = toSet;
	}

}
