package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class PIDarmexperimentPIDSubsystem extends PIDSubsystem {

	private TalonSRX armTalon = new TalonSRX(1);
	private Encoder armAngleEncoder = new Encoder(3, 4, false,
			Encoder.EncodingType.k4X);
	public ArmextenderPIDSubsystem armExtender = new ArmextenderPIDSubsystem();

	// Initialize your subsystem here
	public PIDarmexperimentPIDSubsystem() {
		// Use these to get going:
		// setSetpoint() - Sets where the PID controller should move the system
		// to
		// enable() - Enables the PID controller.
		super("Arm", 1.0, 0.0, 0.0);
		setSetpoint(0.0);
		enable();
		armAngleEncoder.setMaxPeriod(.1);
		armAngleEncoder.setMinRate(10);
		armAngleEncoder.setDistancePerPulse(5);
		armAngleEncoder.setReverseDirection(true);
		armAngleEncoder.setSamplesToAverage(7);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public double getEncoderangle() {
		return Math.abs(armAngleEncoder.get());
	}

	public void stopExtension() {
		setSetpoint(getEncoderangle());
	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		return Math.abs(armAngleEncoder.get());
	}

	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
		armTalon.set(output);
	}

}
