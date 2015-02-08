package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.sensorFilter.Lidar;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveWithLidarSubsystem extends Subsystem {
	private PIDController pid;
	private double distance;
	private Jaguar frontLeft;
	private Jaguar frontRight;
	private Jaguar backLeft;
	private Jaguar backRight;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void initializeDriveTrain() {
		PIDRobotDrive chassis = new PIDRobotDrive(frontLeft, frontRight,
				backLeft, backRight);
		Lidar lidar = new Lidar(Port.kMXP);
		distance = lidar.getDistance();
		pid = new PIDController(0.5, 0, 0.4, lidar, chassis);
	}

	public void runDriveTrain() {
		pid.enable();
		pid.setSetpoint(20);
	}

	public double getDistanceFromLidar() {
		return distance;
	}
}
