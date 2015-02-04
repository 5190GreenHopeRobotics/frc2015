package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class PIDDriveSystem implements Runnable {

	public static final double P = 0.5;
	public static final double I = 0;
	public static final double D = 0.1;
	protected RobotDrive tankDrive;
	protected PIDEncoderDriveTrain encoder;
	protected PIDController left, right;
	protected boolean enabled = false;

	PIDDriveSystem(RobotDrive tankDrive, PIDEncoderDriveTrain encoders) {
		this.tankDrive = tankDrive;
		this.encoder = encoders;
		left = new PIDController(P, I, D, encoders.getLeft(), new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// dummy

			}

		});

		right = new PIDController(P, I, D, encoders.getRight(),
				new PIDOutput() {

					@Override
					public void pidWrite(double output) {
						// dummy

					}

				});

		// left.setInputRange(0, 300);
		// right.setInputRange(0, 300);
	}

	public void enable() {
		left.enable();
		right.enable();
		synchronized (this) {
			enabled = true;
		}
	}

	public void disable() {
		left.disable();
		right.disable();
		synchronized (this) {
			enabled = false;
		}
	}

	public void setPoint(double point) {
		right.setSetpoint(point);
		left.setSetpoint(point);
	}

	public void run() {
		while (!Thread.interrupted()) {
			// if (right.onTarget() && left.onTarget()) {
			// continue;
			// }

			synchronized (this) {
				if (enabled) {
					tankDrive.tankDrive(left.get(), right.get());
				}
			}
		}
	}

}
