package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class PIDRobotDrive extends RobotDrive implements PIDOutput {

	protected Gyro gyro;

	public PIDRobotDrive(SpeedController frontLeftMotor,
			SpeedController rearLeftMotor, SpeedController frontRightMotor,
			SpeedController rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		this.drive(output, -gyro.getAngle() * DriveTrainSubsystem.kP);
	}

	public void setGyro(Gyro toSet) {
		gyro = toSet;
	}

}
